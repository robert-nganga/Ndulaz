package features.shop.presentation.screens.home_screen

import features.shop.domain.models.Shoe

data class HomeScreenState(
    val shoesState: HomeScreenShoesState = HomeScreenShoesState.Loading,
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "",
)

sealed interface HomeScreenShoesState{
    data class Success(val shoes: List<Shoe>) : HomeScreenShoesState
    data class Error(val errorMessage: String) : HomeScreenShoesState
    data object Idle : HomeScreenShoesState
    data object Loading : HomeScreenShoesState
}
