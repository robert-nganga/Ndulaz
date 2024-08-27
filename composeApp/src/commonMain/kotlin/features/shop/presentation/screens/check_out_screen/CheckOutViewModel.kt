package features.shop.presentation.screens.check_out_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robert.request.OrderRequest
import core.data.utils.DataResult
import features.shop.domain.models.OrderItem
import features.shop.domain.models.PaymentMethod
import features.shop.domain.models.ShippingAddress
import features.shop.domain.repository.OrderRepository
import features.shop.domain.repository.ShippingAddressRepository
import features.shop.domain.request.OrderItemRequest
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

    fun createOrder(items: List<OrderItemRequest>) = viewModelScope.launch {
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
                _checkOutScreenState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                println(response.data)
                _checkOutScreenState.update {
                    it.copy(
                        isLoading = false
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