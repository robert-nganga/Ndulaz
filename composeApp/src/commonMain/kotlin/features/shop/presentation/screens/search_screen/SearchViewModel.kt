package features.shop.presentation.screens.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.utils.DataResult
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

    private val _suggestions = MutableStateFlow<List<Shoe>>(emptyList())

    private val _searchState = MutableStateFlow<SearchScreenState>(SearchScreenState.Idle(_suggestions.value))
    val searchState = _searchState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private var searchJob: Job? = null

    init {
        fetchSuggestions()
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
                _searchState.update { SearchScreenState.Idle(_suggestions.value) }
            }
        }
    }

    private fun fetchSuggestions() = viewModelScope.launch {
        when(val result = shoesRepository.getShoes(page = 1, limit = 20)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {}
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _suggestions.update { result.data }
                if (_searchState.value is SearchScreenState.Idle){
                    _searchState.update { SearchScreenState.Idle(result.data) }
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