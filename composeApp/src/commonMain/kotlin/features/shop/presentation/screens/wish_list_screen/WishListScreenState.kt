package features.shop.presentation.screens.wish_list_screen

import features.shop.domain.models.WishListItem


data class WishListScreenState(
    val wishListState: WishListState = WishListState.Loading,
    val removeFromWishListMessage: String? = null,
    val removeFromWishListError: Boolean = false
)
sealed interface WishListState {
    data object Loading : WishListState
    data class Success(val items: List<WishListItem>) : WishListState
    data class Failure(val message: String) : WishListState
    data object Empty : WishListState

}
