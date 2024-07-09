package features.shop.presentation.screens.product_details_screen

import androidx.lifecycle.ViewModel
import features.shop.domain.models.Shoe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductDetailsViewModel: ViewModel(){

    private val _productDetailsState = MutableStateFlow(ProductDetailsState())
    val productDetailsState = _productDetailsState.asStateFlow()


    fun onProductSelected(product: Shoe){
        _productDetailsState.update {
            it.copy(
                product = product
            )
        }
    }
}