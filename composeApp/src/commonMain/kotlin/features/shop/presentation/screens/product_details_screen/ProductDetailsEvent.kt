package features.shop.presentation.screens.product_details_screen

import features.shop.domain.models.Size

sealed interface ProductDetailsEvent {
    data class OnSizeSelected(val size: Size): ProductDetailsEvent
    data class OnQuantityChange(val newQuantity: Int): ProductDetailsEvent
    data object OnAddToCart : ProductDetailsEvent
}