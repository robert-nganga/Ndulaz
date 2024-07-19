package features.shop.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class Shoe(
    val id: Int,
    val brand: Brand?,
    val category: String,
    val createdAt: String,
    val description: String,
    val images: List<String>,
    val name: String,
    val price: Double,
    val productType: String,
    val variants: List<ShoeVariant>
)