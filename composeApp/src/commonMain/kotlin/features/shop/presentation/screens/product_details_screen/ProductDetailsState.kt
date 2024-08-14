package features.shop.presentation.screens.product_details_screen

import features.shop.domain.models.CartItem
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
    val snackBarMessage: String? = null,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val addToWishListMessage: String? = null,
    val addToWishListError: Boolean = false,
    val addToCartState: AddToCartState = AddToCartState.Loading
){
    fun toCartItem(): CartItem {
        return CartItem(
            shoeId = product?.id!!,
            quantity = quantity,
            color = selectedColor,
            size = selectedSize,
            price = selectedVariation?.price!!,
            imageUrl = selectedImage,
            name = product.name,
            brand = product.brand?.name,
            variationId = selectedVariation.id,
            id = 0
        )
    }
}

sealed interface AddToCartState{
    data class Idle(val item: CartItem): AddToCartState
    data object Loading: AddToCartState
    data class Success(val message: String): AddToCartState
    data class Error(val message: String): AddToCartState
}
