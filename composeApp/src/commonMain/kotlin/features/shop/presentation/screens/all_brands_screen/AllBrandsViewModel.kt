package features.shop.presentation.screens.all_brands_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.utils.DataResult
import features.shop.domain.repository.ShoesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllBrandsViewModel(
    private val shoesRepository: ShoesRepository
): ViewModel() {

    private val _allBrandsState = MutableStateFlow<AllBrandsState>(AllBrandsState.Loading)
    val allBrandsState = _allBrandsState.asStateFlow()




    fun fetchAllBrands() = viewModelScope.launch {
        _allBrandsState.value = AllBrandsState.Loading
        when(val result = shoesRepository.getAllBrands()){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _allBrandsState.value = AllBrandsState.Failure(result.message)
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _allBrandsState.value = AllBrandsState.Success(result.data)
            }
        }
    }
}