package features.shop.presentation.screens.brand_screen

import features.shop.domain.models.Brand
import features.shop.domain.models.FilterOptions
import features.shop.domain.models.Shoe

data class BrandScreenState(
    val filterOptions: FilterOptions = FilterOptions(),
    val categories: List<String> = emptyList(),
    val brandShoeListState: BrandShoeListState = BrandShoeListState.Loading
)

sealed interface BrandShoeListState{
    data class Success(val shoes: List<Shoe>): BrandShoeListState
    data class Failure(val message: String): BrandShoeListState
    data object Loading: BrandShoeListState
}
