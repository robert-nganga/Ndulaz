package features.shop.presentation.screens.home_screen

sealed interface HomeScreenEvents {
    data object OnFetchPopularShoes : HomeScreenEvents
    data object OnFetchCategories : HomeScreenEvents
    data object OnFetchBrands : HomeScreenEvents
    data class OnSelectCategory(val category : String) : HomeScreenEvents


}