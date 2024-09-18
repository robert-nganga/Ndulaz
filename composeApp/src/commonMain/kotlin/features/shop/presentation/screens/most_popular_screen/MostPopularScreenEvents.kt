package features.shop.presentation.screens.most_popular_screen

import features.shop.domain.models.FilterOptions

sealed interface MostPopularScreenEvents {
    data object OnFetchBrands: MostPopularScreenEvents
    data object OnFetchCategories: MostPopularScreenEvents
    data class OnFilterOptionsChange(val filterOptions: FilterOptions): MostPopularScreenEvents
}