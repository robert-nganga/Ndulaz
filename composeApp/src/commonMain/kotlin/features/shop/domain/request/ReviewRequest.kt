package features.shop.domain.request

import kotlinx.serialization.Serializable

@Serializable
data class ReviewRequest (
    val userId: Int,
    val orderItemId: Int,
    val userName: String,
    val userImage: String,
    val shoeId: Int,
    val rating: Double,
    val comment: String,
)