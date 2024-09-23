package features.shop.presentation.screens.category_screen

import features.shop.domain.models.Brand
import features.shop.domain.models.FilterOptions
import features.shop.domain.models.Shoe

data class CategoryScreenState(
    val filterOptions: FilterOptions = FilterOptions(),
    val brands: List<String> = emptyList(),
    val categoryScreenShoesState: CategoryScreenShoesState = CategoryScreenShoesState.Loading
)

sealed interface CategoryScreenShoesState {
    data object Loading: CategoryScreenShoesState
    data class Error(val message: String): CategoryScreenShoesState
    data class Success(val shoes: List<Shoe>): CategoryScreenShoesState

}