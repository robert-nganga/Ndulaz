package features.shop.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val id: Int,
    val userId: Int,
    val userName: String,
    val userImage: String,
    val shoeId: Int,
    val rating: Double,
    val comment: String,
    val createdAt: String,
)
