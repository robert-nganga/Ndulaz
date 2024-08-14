package features.shop.presentation.screens.check_out_screen

import androidx.lifecycle.ViewModel
import features.shop.domain.models.PaymentMethod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CheckOutViewModel(): ViewModel() {

    private val _checkOutScreenState = MutableStateFlow(CheckOutScreenState())
    val checkOutScreenState = _checkOutScreenState.asStateFlow()




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