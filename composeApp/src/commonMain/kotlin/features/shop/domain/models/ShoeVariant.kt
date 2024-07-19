package features.shop.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ShoeVariant(
    val id: Int,
    val image: String?,
    val price: Double,
    val size: Int,
    val color: String,
    val quantity: Int
)