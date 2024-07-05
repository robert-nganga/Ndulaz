package features.shop.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Size(
    val quantity: Int,
    val size: Int
)