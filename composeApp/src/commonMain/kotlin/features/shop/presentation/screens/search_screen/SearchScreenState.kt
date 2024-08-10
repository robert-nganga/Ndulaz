package features.shop.presentation.screens.search_screen

import features.shop.domain.models.Shoe

sealed interface SearchScreenState {
    data class Error(val error: String) : SearchScreenState
    data class Success(val results: List<Shoe>) : SearchScreenState
    data object Loading : SearchScreenState
    data class Idle(val suggestions: List<Shoe>): SearchScreenState

}
