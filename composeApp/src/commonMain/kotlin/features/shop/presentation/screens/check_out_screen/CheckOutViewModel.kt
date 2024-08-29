package features.shop.presentation.screens.check_out_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.utils.DataResult
import core.domain.exceptions.BadRequestException
import features.shop.domain.models.PaymentMethod
import features.shop.domain.models.ShippingAddress
import features.shop.domain.repository.OrderRepository
import features.shop.domain.repository.ShippingAddressRepository
import features.shop.domain.request.OrderItemRequest
import features.shop.domain.utils.parseOutOfStockError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CheckOutViewModel(
    private val shippingAddressRepository: ShippingAddressRepository,
    private val orderRepository: OrderRepository
): ViewModel() {

    val savedShippingAddresses = shippingAddressRepository
        .getAllAddress()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _checkOutScreenState = MutableStateFlow(CheckOutScreenState())
    val checkOutScreenState = _checkOutScreenState.asStateFlow()


    fun resetState(){
        _checkOutScreenState.value = CheckOutScreenState()
    }

    fun resetErrorMessage(){
        _checkOutScreenState.update {
            it.copy(
                errorMessage = null
            )
        }
    }

    fun createOrder(items: List<OrderItemRequest>) = viewModelScope.launch {
        if (_checkOutScreenState.value.selectedAddress == null){
            _checkOutScreenState.update {
                it.copy(
                    errorMessage = "Please select a shipping address"
                )
            }
            return@launch
        }
        val request = _checkOutScreenState.value.createOrderRequest(items)
        _checkOutScreenState.update {
            it.copy(
                isLoading = true
            )
        }
        val response = orderRepository.createOrder(request)
        when(response){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                println(response.message)
                if (response.exc is BadRequestException && response.message != "Bad request"){
                    val list = response.message.parseOutOfStockError()
                    println(list)
                    _checkOutScreenState.update {
                        it.copy(
                            outOfStockItems = list,
                            isLoading = false
                        )
                    }
                } else {
                    _checkOutScreenState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Something went wrong, try again later"
                        )
                    }
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                println(response.data)
                _checkOutScreenState.update {
                    it.copy(
                        isLoading = false,
                        isCheckOutSuccessful = true
                    )
                }
            }
        }
    }


    fun updateSelectedShippingAddress(address: ShippingAddress){
        _checkOutScreenState.update {
            it.copy(
                selectedAddress = address
            )
        }
    }

    fun updateSelectedMethod(paymentMethod: PaymentMethod){
        _checkOutScreenState.update { screenState ->
            screenState.copy(
                selectedPaymentMethod = paymentMethod
            )
        }
    }

    fun updateTotalPrice(price: Double){
        _checkOutScreenState.update { screenState ->
            screenState.copy(
                totalPrice = price
            )
        }
    }



}