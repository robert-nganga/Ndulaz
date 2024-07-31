package features.shop.presentation.screens.wish_list_screen

import features.shop.domain.models.WishListItem

sealed interface WishListScreenState {
    data object Loading : WishListScreenState
    data class Success(val items: List<WishListItem>) : WishListScreenState
    data class Failure(val message: String) : WishListScreenState

}
