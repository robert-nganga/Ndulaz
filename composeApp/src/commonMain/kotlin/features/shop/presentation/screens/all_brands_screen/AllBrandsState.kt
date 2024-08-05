package features.shop.presentation.screens.all_brands_screen

import features.shop.domain.models.Brand

sealed interface AllBrandsState {
    data class Success(val brands: List<Brand>) : AllBrandsState
    data object Loading : AllBrandsState
    data class Failure(val message: String) : AllBrandsState
}
