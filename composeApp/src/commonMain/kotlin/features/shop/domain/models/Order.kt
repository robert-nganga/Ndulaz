package features.shop.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: Int,
    val userId: Int,
    val items: List<OrderItem>,
    val totalAmount: Double,
    val status: OrderStatus,
    val shippingAddress: ShippingAddress,
    val createdAt: String
)



enum class OrderStatus {
    PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}
