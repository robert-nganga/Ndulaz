package features.shop.presentation.screens.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.preferences.SessionHandler
import core.data.utils.DataResult
import features.profile.domain.utils.errorMessage
import features.shop.domain.repository.ShoesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val sessionHandler: SessionHandler,
    private val repository: ShoesRepository
):ViewModel() {


    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()


    init {
        getShoes()
        getCategories()
        selectedCategory("All")
    }

    fun getShoes() = viewModelScope.launch {
        _homeScreenState.update {
            it.copy(
                shoesState = HomeScreenShoesState.Loading
            )
        }
        when (val result = repository.getShoes(page = 1, limit = 15)) {
            is DataResult.Empty -> {}
            is DataResult.Loading -> {}
            is DataResult.Error -> {
                _homeScreenState.update {
                    it.copy(
                        shoesState = HomeScreenShoesState.Error(
                            errorMessage = result.exc?.errorMessage() ?: "Unknown error"
                        )
                    )
                }
            }
            is DataResult.Success -> {
                _homeScreenState.update {
                    it.copy(
                        shoesState = HomeScreenShoesState.Success(
                            shoes = result.data
                        )
                    )
                }
            }

        }

    }

    fun selectedCategory(category: String) {
        _homeScreenState.update {
            it.copy(
                selectedCategory = category
            )
        }
    }

    fun getCategories() = viewModelScope.launch {
        val response = repository.getCategories()
        when(response){
            is DataResult.Empty -> {}
            is DataResult.Loading -> {}
            is DataResult.Error -> {}
            is DataResult.Success -> {
                val categories = response.data.map { it.name } + "All"
                _homeScreenState.update {
                    it.copy(
                        categories = categories
                    )
                }
            }
        }
    }

    fun logout() = viewModelScope.launch {
        sessionHandler.clearSession()
    }

}