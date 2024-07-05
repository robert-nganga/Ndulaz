package features.shop.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class Shoe(
    val brand: String,
    val category: String,
    val createdAt: String,
    val description: String,
    val id: Int,
    val images: List<String>,
    val name: String,
    val price: Double,
    val sizes: List<Size>
)