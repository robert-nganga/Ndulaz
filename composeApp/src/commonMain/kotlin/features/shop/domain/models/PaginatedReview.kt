package features.shop.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedReview(
    val reviews: List<Review>,
    val totalCount: Int
)
