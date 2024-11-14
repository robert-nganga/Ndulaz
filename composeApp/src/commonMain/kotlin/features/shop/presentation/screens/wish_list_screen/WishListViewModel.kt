package features.shop.presentation.screens.wish_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.utils.DataResult
import features.shop.domain.models.WishList
import features.shop.domain.repository.WishListRepository
import features.shop.presentation.screens.home_screen.PopularShoesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishListViewModel(
    private val wishListRepository: WishListRepository
): ViewModel() {


    private val _wishListScreenState = MutableStateFlow(WishListScreenState())
    val wishListScreenState = _wishListScreenState.asStateFlow()


    fun updateWishList(wishList: WishList){
        _wishListScreenState.update {
            it.copy(
                wishListState = WishListState.Success(wishList.items)
            )
        }
    }

    fun resetWishListMessage(){
        _wishListScreenState.update {
            it.copy(
                removeFromWishListMessage = null,
            )
        }
    }

    fun fetchMyWishList() = viewModelScope.launch {
        _wishListScreenState.update {
            it.copy(wishListState = WishListState.Loading)
        }
        when(val result = wishListRepository.getWishList()){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _wishListScreenState.update {
                    it.copy(wishListState = WishListState.Failure(result.message))
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _wishListScreenState.update {
                    it.copy(wishListState = WishListState.Success(result.data.items))

                }
            }
        }
    }

    fun clearMyWishList() = viewModelScope.launch {
        when(val result = wishListRepository.clearWishList()){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _wishListScreenState.update {
                    it.copy(wishListState = WishListState.Failure(result.message))
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _wishListScreenState.update {
                    it.copy(wishListState = WishListState.Success(emptyList()))
                }
            }
        }
    }


    fun removeItemFromWishList(shoeId: Int) = viewModelScope.launch {
        _wishListScreenState.update {
            it.copy(
                removeFromWishListError = false,
            )
        }
        when(val response = wishListRepository.removeItemFromWishList(shoeId)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _wishListScreenState.update {
                    it.copy(
                        removeFromWishListMessage = "Couldn't remove item from wish list",
                        removeFromWishListError = true
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _wishListScreenState.update {
                    it.copy(
                        removeFromWishListMessage = "Removed item from wish list",
                        wishListState = WishListState.Success(response.data.items)
                    )
                }
            }
        }
    }

//    private fun updateItemWishListStatus(shoeId: Int) {
//        if (_wishListScreenState.value.wishListState is WishListState.Success){
//            val shoes = (_wishListScreenState.value.wishListState as WishListState.Success).items
//            val new = shoes.map {
//                if(it.id == shoeId){
//                    it.copy(
//                        shoe = it.shoe.copy(isInWishList = false)
//                    )
//                } else {
//                    it
//                }
//            }
//            _wishListScreenState.update {
//                it.copy(
//                    wishListState = WishListState.Success(
//                        items = new
//                    )
//                )
//            }
//        }
//    }






}