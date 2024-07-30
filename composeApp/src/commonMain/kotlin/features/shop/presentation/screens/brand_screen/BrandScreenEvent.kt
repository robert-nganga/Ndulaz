package features.shop.presentation.screens.brand_screen

sealed interface BrandScreenEvent {
    data class OnUpdateBrand(val brand: String): BrandScreenEvent
}