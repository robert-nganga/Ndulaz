package features.shop.presentation.screens.cart_screen

import features.shop.domain.models.CartItem

data class CartScreenState(
    val cartItems: List<CartItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
