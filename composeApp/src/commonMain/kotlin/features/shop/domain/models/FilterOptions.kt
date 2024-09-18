package features.shop.domain.models

data class FilterOptions(
    val category: String? = null,
    val brand: String? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val sortBy: String = "recency",
    val sortOrder: String = "asc",
    val page: Int = 1,
    val pageSize: Int = 15
)
