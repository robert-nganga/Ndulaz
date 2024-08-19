package features.shop.presentation.screens.check_out_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import features.shop.domain.models.PaymentMethod
import features.shop.domain.models.ShippingAddress
import features.shop.domain.repository.ShippingAddressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CheckOutViewModel(
    private val shippingAddressRepository: ShippingAddressRepository
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