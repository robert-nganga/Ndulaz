package features.shop.presentation.screens.most_popular_screen

import features.shop.domain.models.FilterOptions
import features.shop.presentation.screens.home_screen.PopularShoesState

data class MostPopularScreenState(
    val categories: List<String> = emptyList(),
    val brands: List<String> = emptyList(),
    val selectedCategory: String = "",
    val popularShoesState: PopularShoesState = PopularShoesState.Idle,
    val filterOptions: FilterOptions = FilterOptions()
)
