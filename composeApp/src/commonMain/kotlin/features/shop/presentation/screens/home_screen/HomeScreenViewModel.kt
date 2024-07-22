package features.shop.presentation.screens.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.preferences.SessionHandler
import core.data.utils.DataResult
import features.profile.domain.utils.parseErrorMessageFromException
import features.shop.domain.repository.ShoesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val sessionHandler: SessionHandler,
    private val repository: ShoesRepository
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
        }
    }

    private fun filterShoesByCategory(category: String) = viewModelScope.launch {
        _homeScreenState.update {
            it.copy(
                popularShoesState = PopularShoesState.Loading
            )
        }
        val result = if(category == "All") repository.getShoes(page = 1, limit = 15) else repository.filterShoesByCategory(category)
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
        when (val result = repository.getAllBrands()) {
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

//    private fun fetchPaginatedShoes() = viewModelScope.launch {
//        _homeScreenState.update {
//            it.copy(
//                popularShoesState = PopularShoesState.Loading
//            )
//        }
//        when (val result = repository.getShoes(page = 1, limit = 15)) {
//            is DataResult.Empty -> {}
//            is DataResult.Loading -> {}
//            is DataResult.Error -> {
//                _homeScreenState.update {
//                    it.copy(
//                        popularShoesState = PopularShoesState.Error(
//                            errorMessage = result.exc?.parseErrorMessageFromException() ?: "Unknown error"
//                        )
//                    )
//                }
//            }
//            is DataResult.Success -> {
//                _homeScreenState.update {
//                    it.copy(popularShoesState = PopularShoesState.Success(shoes = result.data))
//                }
//            }
//        }
//    }

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
        when(val response = repository.getCategories()){
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