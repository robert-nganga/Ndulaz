package features.shop.domain.models

import features.shop.domain.request.OrderItemRequest
import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String,
    val shoeId: Int,
    val brand: String?,
    val size: Int,
    val color: String,
    val variationId: Int
)

fun CartItem.toOrderItemRequest() = OrderItemRequest(
    shoeId = shoeId,
    variantId = variationId,
    quantity = quantity,
    price = price
)