package features.shop.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class WishList(
    val createdAt: String,
    val id: Int,
    val items: List<WishListItem>,
    val updatedAt: String
)