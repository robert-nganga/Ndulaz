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
    PAYMENTCONFIRMED, PROCESSING, INDELIVERY, COMPLETED
}

fun OrderStatus.toLocalizedString(): String {
    return when(this){
        OrderStatus.PROCESSING -> "Processing"
        OrderStatus.INDELIVERY -> "In Delivery"
        OrderStatus.COMPLETED -> "Completed"
        OrderStatus.PAYMENTCONFIRMED -> "Payment Processed"
    }
}
