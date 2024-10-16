package features.shop.domain.models

data class ReviewFilterOptions(
    val rating: Double? = null,
    val page: Int = 1,
    val pageSize: Int = 15
)
