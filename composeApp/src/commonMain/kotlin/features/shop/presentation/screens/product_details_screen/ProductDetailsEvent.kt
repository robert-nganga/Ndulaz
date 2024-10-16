package features.shop.presentation.screens.product_details_screen

import features.shop.domain.models.CartItem
import features.shop.domain.models.ReviewFilterOptions

sealed interface ProductDetailsEvent {
    data class OnSizeSelected(val size: Int): ProductDetailsEvent
    data class OnColorSelected(val color: String): ProductDetailsEvent
    data class OnQuantityChange(val newQuantity: Int): ProductDetailsEvent
    data class OnImageSelected(val image: String): ProductDetailsEvent
    data class OnSaveCartItem(val item: CartItem): ProductDetailsEvent
    data class OnReviewFilterOptionsUpdate(val options: ReviewFilterOptions, val shoeId: Int): ProductDetailsEvent
    data object OnSeeMoreReviews: ProductDetailsEvent
    data object OnWishListIconClick : ProductDetailsEvent
    data object OnResetError: ProductDetailsEvent
    data object OnAddToCart: ProductDetailsEvent
}