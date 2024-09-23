package features.shop.presentation.screens.brand_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.utils.DataResult
import features.profile.domain.utils.parseErrorMessageFromException
import features.shop.domain.models.FilterOptions
import features.shop.domain.repository.ShoesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BrandScreenViewModel(
    private val repository: ShoesRepository
): ViewModel() {

    private val _brandScreenState = MutableStateFlow(BrandScreenState())
    val brandScreenState = _brandScreenState.asStateFlow()



    fun updateFilterOptions(filterOptions: FilterOptions) {
        _brandScreenState.update {
            it.copy(
                filterOptions = filterOptions
            )
        }
        filterShoes(filterOptions)
    }

    fun fetchAllCategories() = viewModelScope.launch {
        when(val response = repository.getCategories()){
            is DataResult.Empty -> {}
            is DataResult.Loading -> {}
            is DataResult.Error -> {}
            is DataResult.Success -> {
                val categories = response.data.map { it.name }.toMutableList()
                categories.add(0, "All")
                _brandScreenState.update {
                    it.copy(
                        categories = categories,
                    )
                }
            }
        }
    }


    private fun filterShoes(filterOptions: FilterOptions) = viewModelScope.launch {
        _brandScreenState.update {
            it.copy(
                brandShoeListState = BrandShoeListState.Loading
            )
        }
        println("filtering shoes $filterOptions")

        when(val response = repository.getAllShoesFiltered(filterOptions)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                println("filtering error")
                _brandScreenState.update {
                    it.copy(
                        brandShoeListState = BrandShoeListState.Failure(response.exc?.parseErrorMessageFromException() ?: "Unknown error")
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                println("filtering success ${response.data.size}")
                _brandScreenState.update {
                    it.copy(
                        brandShoeListState = BrandShoeListState.Success(response.data)
                    )
                }
            }
        }
    }
}