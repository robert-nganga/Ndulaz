package features.shop.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class WishListItem(
    val createdAt: String,
    val id: Int,
    val shoe: Shoe
)