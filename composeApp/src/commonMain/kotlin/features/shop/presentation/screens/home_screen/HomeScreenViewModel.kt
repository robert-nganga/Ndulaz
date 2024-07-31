package features.shop.presentation.screens.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.preferences.SessionHandler
import core.data.utils.DataResult
import features.profile.domain.utils.parseErrorMessageFromException
import features.shop.domain.repository.ShoesRepository
import features.shop.domain.repository.WishListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val sessionHandler: SessionHandler,
    private val shoesRepository: ShoesRepository,
    private val wishListRepository: WishListRepository
):ViewModel() {


    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

    val user = sessionHandler.getUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )


    fun onEvent(event: HomeScreenEvents){
        when(event){
            is HomeScreenEvents.OnFetchBrands -> {
                fetchAllBrands()
            }
            is HomeScreenEvents.OnFetchCategories -> {
                fetchAllCategories()
            }
            is HomeScreenEvents.OnSelectCategory -> {
                updateSelectedCategory(event.category)
                filterShoesByCategory(event.category)
            }

            is HomeScreenEvents.OnWishListIconClicked -> {
                val shoe = event.shoe
                if(event.shoe.isInWishList){
                    removeItemFromWishList(shoe.id)
                } else {
                    addItemToWishList(shoe.id)
                }
            }
        }
    }

    fun resetWishListMessage() {
        _homeScreenState.update {
            it.copy(
                addToWishListMessage = null,
            )
        }
    }

    private fun addItemToWishList(shoeId: Int) = viewModelScope.launch {
        _homeScreenState.update {
            it.copy(
                addToWishListError = false,
            )
        }
        when(val response = wishListRepository.addItemToWishList(shoeId)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _homeScreenState.update {
                    it.copy(
                        addToWishListMessage = "Couldn't add item to wish list",
                        addToWishListError = true
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _homeScreenState.update {
                    it.copy(
                        addToWishListMessage = "Item added to wish list"
                    )
                }
                updateItemWishListStatus(shoeId)
            }
        }
    }

    private fun removeItemFromWishList(shoeId: Int) = viewModelScope.launch {
        _homeScreenState.update {
            it.copy(
                addToWishListError = false,
            )
        }
        when(val response = wishListRepository.removeItemFromWishList(shoeId)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _homeScreenState.update {
                    it.copy(
                        addToWishListMessage = "Couldn't remove item from wish list",
                        addToWishListError = true
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _homeScreenState.update {
                    it.copy(
                        addToWishListMessage = "Removed item from wish list"
                    )
                }
                updateItemWishListStatus(shoeId)
            }
        }
    }

    private fun updateItemWishListStatus(shoeId: Int) {
        if (_homeScreenState.value.popularShoesState is PopularShoesState.Success){
            val shoes = (_homeScreenState.value.popularShoesState as PopularShoesState.Success).shoes
            val new = shoes.map {
                if(it.id == shoeId){
                    it.copy(
                        isInWishList = !it.isInWishList
                    )
                } else {
                    it
                }
            }
            _homeScreenState.update {
                it.copy(
                    popularShoesState = PopularShoesState.Success(
                        shoes = new
                    )
                )
            }
        }
    }

    private fun filterShoesByCategory(category: String) = viewModelScope.launch {
        _homeScreenState.update {
            it.copy(
                popularShoesState = PopularShoesState.Loading
            )
        }
        val result = if(category == "All") shoesRepository.getShoes(page = 1, limit = 15) else shoesRepository.filterShoesByCategory(category)
        when (result) {
            is DataResult.Empty -> {}
            is DataResult.Loading -> {}
            is DataResult.Error -> {
                _homeScreenState.update {
                    it.copy(
                        popularShoesState = PopularShoesState.Error(
                            errorMessage = result.exc?.parseErrorMessageFromException() ?: "Unknown error"
                        )
                    )
                }
            }
            is DataResult.Success -> {
                _homeScreenState.update {
                    it.copy(popularShoesState = PopularShoesState.Success(shoes = result.data))
                }
            }
        }
    }

    private fun fetchAllBrands() = viewModelScope.launch {
        _homeScreenState.update {
            it.copy(
                brandsState = BrandsState.Loading
            )
        }
        when (val result = shoesRepository.getAllBrands()) {
            is DataResult.Empty -> {}
            is DataResult.Loading -> {}
            is DataResult.Error -> {
                _homeScreenState.update {
                    it.copy(
                        brandsState = BrandsState.Error
                    )
                }
            }
            is DataResult.Success -> {
                _homeScreenState.update {
                    it.copy(brandsState = BrandsState.Success(brands = result.data))
                }
            }
        }
    }

    private fun updateSelectedCategory(category: String) {
        _homeScreenState.update {
            it.copy(
                selectedCategory = category
            )
        }
    }

    private fun fetchAllCategories() = viewModelScope.launch {
        _homeScreenState.update {
            it.copy( categoriesState = CategoriesState.Loading )
        }
        when(val response = shoesRepository.getCategories()){
            is DataResult.Empty -> {}
            is DataResult.Loading -> {}
            is DataResult.Error -> {
                _homeScreenState.update {
                    it.copy( categoriesState = CategoriesState.Error )
                }
            }
            is DataResult.Success -> {
                val categories = response.data.map { it.name }.toMutableList()
                categories.add(0, "All")
                _homeScreenState.update {
                    it.copy(
                        categoriesState = CategoriesState.Success(categories.toList())
                    )
                }
            }
        }
    }

    fun logout() = viewModelScope.launch {
        sessionHandler.clearSession()
    }

}