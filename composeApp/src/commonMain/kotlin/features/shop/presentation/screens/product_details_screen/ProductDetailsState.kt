package features.shop.presentation.screens.product_details_screen

import features.shop.domain.models.Shoe
import features.shop.domain.models.Size

data class ProductDetailsState(
    val product: Shoe? = null,
    val selectedSize: Size = Size(0,0),
    val quantity: Int = 1,
)
