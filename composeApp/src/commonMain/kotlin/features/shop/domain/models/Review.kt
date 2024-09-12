package features.shop.domain.models

data class Review (
    val id: String,
    val rating: Int,
    val description: String,
    val name: String,
    val date: String,
    val title: String
)
