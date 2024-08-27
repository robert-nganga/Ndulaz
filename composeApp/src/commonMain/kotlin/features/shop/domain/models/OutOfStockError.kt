package features.shop.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class OutOfStockError(
    val message: String,
    val shoeId: Int,
    val variationId: Int
)
