package features.shop.presentation.screens.cart_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import features.shop.domain.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
): ViewModel() {

    val cartItems = cartRepository
        .getAllCartItems()
        .onEach {
            println(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )


    fun clearCart() = viewModelScope.launch {
        cartRepository.deleteAllCartItems()
    }
}