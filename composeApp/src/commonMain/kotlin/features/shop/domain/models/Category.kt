package features.shop.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val description: String,
    val id: Int,
    val name: String,
    val image: String
)