package features.shop.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class ShoeResponse(
    val shoes: List<Shoe>,
    val totalCount: Int
)