package features.shop.domain.models

data class CartItem(
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String,
    val shoeId: Int,
    val brand: String,
    val size: Int,
    val color: String,
    val variationId: Int
)