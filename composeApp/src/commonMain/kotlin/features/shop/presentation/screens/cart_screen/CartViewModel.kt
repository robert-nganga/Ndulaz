package features.shop.presentation.screens.cart_screen

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import features.shop.domain.models.CartItem
import features.shop.domain.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
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
            println("Viewmodel: $it")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalPrice = cartItems
        .map { items ->
            items.sumOf { it.price * it.quantity }
        }

    init {
        println("CartViewModel init")
    }

    fun updateItemQuantity(item: CartItem, newQuantity: Int) = viewModelScope.launch {
        val newItem = item.copy(quantity = newQuantity)
        cartRepository.upsertCartItem(newItem)
    }

    fun clearCart() = viewModelScope.launch {
        cartRepository.deleteAllCartItems()
    }
}