package features.shop.presentation.screens.product_details_screen

sealed interface ProductDetailsEvent {
    data class OnSizeSelected(val size: Int): ProductDetailsEvent
    data class OnColorSelected(val color: String): ProductDetailsEvent
    data class OnQuantityChange(val newQuantity: Int): ProductDetailsEvent
    data class OnImageSelected(val image: String): ProductDetailsEvent
    data object OnAddToCart : ProductDetailsEvent
}