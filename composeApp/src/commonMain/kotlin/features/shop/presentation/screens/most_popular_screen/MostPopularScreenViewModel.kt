package features.shop.presentation.screens.most_popular_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.utils.DataResult
import features.profile.domain.utils.parseErrorMessageFromException
import features.shop.domain.repository.ShoesRepository
import features.shop.presentation.screens.home_screen.PopularShoesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MostPopularScreenViewModel(
    private val repository: ShoesRepository
): ViewModel() {

    private val _mostPopularScreenState = MutableStateFlow(MostPopularScreenState())
    val mostPopularScreenState = _mostPopularScreenState.asStateFlow()


    fun onEvent(event: MostPopularScreenEvents){
        when(event){
            is MostPopularScreenEvents.OnCategorySelected -> {
                _mostPopularScreenState.update {
                    it.copy(
                        selectedCategory = event.category
                    )
                }
                filterShoesByCategory(event.category)
            }

            is MostPopularScreenEvents.OnFetchCategories -> {
                fetchAllCategories()
            }
        }
    }

    private fun filterShoesByCategory(category: String) = viewModelScope.launch {
        _mostPopularScreenState.update {
            it.copy(
                popularShoesState = PopularShoesState.Loading
            )
        }
        val result = if(category == "All") repository.getShoes(page = 1, limit = 15) else repository.filterShoesByCategory(category)
        when (result) {
            is DataResult.Empty -> {}
            is DataResult.Loading -> {
                _mostPopularScreenState.update {
                    it.copy(
                        popularShoesState = PopularShoesState.Loading
                    )
                }
            }
            is DataResult.Error -> {
                _mostPopularScreenState.update {
                    it.copy(
                        popularShoesState = PopularShoesState.Error(
                            errorMessage = result.exc?.parseErrorMessageFromException() ?: "Unknown error"
                        )
                    )
                }
            }
            is DataResult.Success -> {
                _mostPopularScreenState.update {
                    it.copy(popularShoesState = PopularShoesState.Success(shoes = result.data))
                }
            }
        }
    }

    private fun fetchAllCategories() = viewModelScope.launch {
        when(val response = repository.getCategories()){
            is DataResult.Empty -> {}
            is DataResult.Loading -> {}
            is DataResult.Error -> {}
            is DataResult.Success -> {
                val categories = response.data.map { it.name }.toMutableList()
                categories.add(0, "All")
                _mostPopularScreenState.update {
                    it.copy(
                        categories = categories,
                    )
                }
            }
        }
    }
}