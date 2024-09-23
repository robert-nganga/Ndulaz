package features.shop.presentation.utils

import features.profile.domain.models.User
import features.shop.domain.models.CartItem
import features.shop.domain.models.Category
import features.shop.domain.models.Order
import features.shop.domain.models.OrderItem
import features.shop.domain.models.ShippingAddress

object NavigationUtils {

    lateinit var cartItems: List<CartItem>
    var shippingAddress: ShippingAddress? = null
    lateinit var currentUser: User
    lateinit var order: Order
    var orderItemId: Int = 0
    lateinit var orderItem: OrderItem
    lateinit var category: Category
}