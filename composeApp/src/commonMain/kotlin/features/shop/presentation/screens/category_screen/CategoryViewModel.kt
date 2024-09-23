package features.shop.presentation.screens.category_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.utils.DataResult
import features.shop.domain.models.FilterOptions
import features.shop.domain.repository.ShoesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: ShoesRepository
): ViewModel() {

    private val _categoryScreenState = MutableStateFlow(CategoryScreenState())
    val categoryScreenState = _categoryScreenState.asStateFlow()



    fun updateFilterOptions(filterOptions: FilterOptions) {
        _categoryScreenState.update {
            it.copy(
                filterOptions = filterOptions
            )
        }
        fetchShoes(filterOptions)
    }

    fun fetchAllBrands() = viewModelScope.launch {
        when(val response = repository.getAllBrands()){
            is DataResult.Empty -> {}
            is DataResult.Loading -> {}
            is DataResult.Error -> {}
            is DataResult.Success -> {
                val brands = response.data.map { it.name }.toMutableList()
                brands.add(0, "All")
                _categoryScreenState.update {
                    it.copy(
                        brands = brands,
                    )
                }
            }
        }
    }

    private fun fetchShoes(filterOptions: FilterOptions) = viewModelScope.launch {
        _categoryScreenState.update {
            it.copy(
                categoryScreenShoesState = CategoryScreenShoesState.Loading
            )
        }
        when(val result = repository.getAllShoesFiltered(filterOptions)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _categoryScreenState.update {
                    it.copy(
                        categoryScreenShoesState = CategoryScreenShoesState.Error(result.message)
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _categoryScreenState.update {
                    it.copy(
                        categoryScreenShoesState = CategoryScreenShoesState.Success(result.data)
                    )
                }
            }
        }
    }

}