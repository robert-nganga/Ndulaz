package features.shop.presentation.screens.check_out_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import features.shop.domain.models.CartItem
import features.shop.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CheckOutViewModel(
    private val cartRepository: CartRepository
): ViewModel() {


    private val _checkOutScreenState = MutableStateFlow(CheckOutScreenState())
    val checkOutScreenState = _checkOutScreenState.asStateFlow()

    val cartItems = cartRepository
        .getAllCartItems()
        .onEach { items ->
            _checkOutScreenState.update { screenState ->
                screenState.copy(
                    totalPrice = items.sumOf { it.price * it.quantity }
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun updateTotalPrice(price: Double){
        _checkOutScreenState.update { screenState ->
            screenState.copy(
                totalPrice = price
            )
        }
    }



}