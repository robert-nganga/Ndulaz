package features.shop.presentation.screens.product_details_screen

import features.shop.domain.models.Shoe
import features.shop.domain.models.ShoeVariant

data class ProductDetailsState(
    val product: Shoe? = null,
    val selectedImage: String = "",
    val sizes: List<Int> = emptyList(),
    val selectedSize: Int = 0,
    val colors: List<String> = emptyList(),
    val selectedColor: String = "",
    val selectedVariation: ShoeVariant? = null,
    val quantity: Int = 1,
    val errorMessage: String? = null,
    val addToWishListMessage: String? = null,
    val addToWishListError: Boolean = false
)
