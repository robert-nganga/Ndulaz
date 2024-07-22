package features.shop.presentation.screens.home_screen

import features.shop.domain.models.Brand
import features.shop.domain.models.Shoe

data class HomeScreenState(
    val popularShoesState: PopularShoesState = PopularShoesState.Loading,
    val categoriesState: CategoriesState = CategoriesState.Loading,
    val brandsState: BrandsState = BrandsState.Loading,
    val selectedCategory: String = "",
)

sealed interface PopularShoesState{
    data class Success(val shoes: List<Shoe>) : PopularShoesState
    data class Error(val errorMessage: String) : PopularShoesState
    data object Idle : PopularShoesState
    data object Loading : PopularShoesState
}

sealed interface CategoriesState{
    data class Success(val categories: List<String>) : CategoriesState
    data object Error : CategoriesState
    data object Loading : CategoriesState
}

sealed interface BrandsState{
    data class Success(val brands: List<Brand>) : BrandsState
    data object Error : BrandsState
    data object Loading : BrandsState
}
