package features.shop.presentation.screens.home_screen

import features.shop.domain.models.Shoe

sealed interface HomeScreenEvents {
    data object OnFetchCategories: HomeScreenEvents
    data object OnFetchBrands: HomeScreenEvents
    data class OnSelectCategory(val category : String): HomeScreenEvents
    data class OnWishListIconClicked(val shoe: Shoe): HomeScreenEvents


}