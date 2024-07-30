package features.shop.presentation.screens.brand_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.utils.DataResult
import features.profile.domain.utils.parseErrorMessageFromException
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



    fun onEvent(event: BrandScreenEvent) {
        when (event) {
            is BrandScreenEvent.OnUpdateBrand -> {
                filterByShoesBrand(event.brand)
            }
        }
    }


    private fun filterByShoesBrand(brand: String) = viewModelScope.launch {
        println("filter by brand")
        _brandScreenState.update {
            it.copy(
                brandShoeListState = BrandShoeListState.Loading
            )
        }
        println("filter by brand state is ${_brandScreenState.value}")
        when(val response = repository.filterShoesByBrand(brand)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _brandScreenState.update {
                    it.copy(
                        brandShoeListState = BrandShoeListState.Failure(response.exc?.parseErrorMessageFromException() ?: "Unknown error")
                    )
                }
                println("filter by brand state is ${_brandScreenState.value}")
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _brandScreenState.update {
                    it.copy(
                        brandShoeListState = BrandShoeListState.Success(response.data)
                    )
                }
                println("filter by brand state is success")
            }
        }
    }
}