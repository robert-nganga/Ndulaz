package features.shop.presentation.screens.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.utils.DataResult
import features.shop.domain.models.Brand
import features.shop.domain.models.Category
import features.shop.domain.models.Shoe
import features.shop.domain.repository.ShoesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel (
    private val shoesRepository: ShoesRepository
): ViewModel() {

    private val _brands = MutableStateFlow<List<Brand>>(emptyList())

    private val _categories = MutableStateFlow<List<Category>>(emptyList())

    private val _searchState = MutableStateFlow<SearchScreenState>(SearchScreenState.Idle(_brands.value, _categories.value))
    val searchState = _searchState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private var searchJob: Job? = null

    init {
        fetchAllCategories()
        fetchAllBrands()
    }



    fun onQueryChange(query: String) {
        _query.update {
            query
        }
        // Creates a delay to avoid making too many network calls
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(700L)
            if (query.length >= 2){
                searchShoes(query)
            }else{
                _searchState.update { SearchScreenState.Idle(_brands.value, _categories.value) }
            }
        }
    }

    fun fetchAllCategories() = viewModelScope.launch {
        when(val response = shoesRepository.getCategories()){
            is DataResult.Empty -> {}
            is DataResult.Loading -> {}
            is DataResult.Error -> {}
            is DataResult.Success -> {
                _categories.update {
                    response.data
                }
                _searchState.update {
                    SearchScreenState.Idle(_brands.value, _categories.value)
                }
            }
        }
    }
    fun fetchAllBrands() = viewModelScope.launch {
        when(val response = shoesRepository.getAllBrands()){
            is DataResult.Empty -> {}
            is DataResult.Loading -> {}
            is DataResult.Error -> {}
            is DataResult.Success -> {
                val brands = response.data
                _brands.update {
                    brands
                }
                _searchState.update {
                    SearchScreenState.Idle(_brands.value, _categories.value)
                }
            }
        }
    }

    private fun searchShoes(query: String) = viewModelScope.launch {
        _searchState.update { SearchScreenState.Loading }
        when(val result = shoesRepository.searchShoes(query)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _searchState.update { SearchScreenState.Error(result.message) }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _searchState.update { SearchScreenState.Success(result.data) }
            }
        }

    }


}