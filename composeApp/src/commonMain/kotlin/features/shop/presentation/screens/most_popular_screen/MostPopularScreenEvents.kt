package features.shop.presentation.screens.most_popular_screen

sealed interface MostPopularScreenEvents {
    data class OnCategorySelected(val category: String): MostPopularScreenEvents
    data object OnFetchCategories: MostPopularScreenEvents
}