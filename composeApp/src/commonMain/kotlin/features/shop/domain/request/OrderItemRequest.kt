package features.shop.domain.request

import kotlinx.serialization.Serializable


@Serializable
data class OrderItemRequest (
    val shoeId: Int,
    val variantId: Int,
    val quantity: Int,
    val price: Double
)
enum class OrderStatus {
    PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}

