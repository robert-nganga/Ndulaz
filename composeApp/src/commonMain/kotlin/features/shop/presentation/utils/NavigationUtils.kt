package features.shop.presentation.utils

import features.shop.domain.models.CartItem
import features.shop.domain.models.ShippingAddress

object NavigationUtils {

    lateinit var cartItems: List<CartItem>
    var shippingAddress: ShippingAddress? = null
}