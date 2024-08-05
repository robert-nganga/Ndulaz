package features.shop.presentation.screens.wish_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.utils.DataResult
import features.shop.domain.models.WishList
import features.shop.domain.repository.WishListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishListViewModel(
    private val wishListRepository: WishListRepository
): ViewModel() {


    private val _wishListState = MutableStateFlow<WishListScreenState>(WishListScreenState.Loading)
    val wishListState = _wishListState.asStateFlow()

    init {
        fetchMyWishList()
        println("WishListViewModel init")
    }

    fun updateWishList(wishList: WishList){
        _wishListState.update {
            WishListScreenState.Success(wishList.items)
        }
    }

    fun fetchMyWishList() = viewModelScope.launch {
        _wishListState.update {
            WishListScreenState.Loading
        }
        when(val result = wishListRepository.getWishList()){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _wishListState.update {
                    WishListScreenState.Failure(result.message)
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _wishListState.update {
                    WishListScreenState.Success(result.data.items)
                }
            }
        }
    }






}