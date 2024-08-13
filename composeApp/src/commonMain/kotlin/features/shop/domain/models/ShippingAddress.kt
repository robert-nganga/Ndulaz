package features.shop.domain.models

data class ShippingAddress(
    val name: String,
    val lat: Double,
    val lng: Double,
    val phone: String
)
