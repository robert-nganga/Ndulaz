package features.shop.presentation.screens.product_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import features.shop.domain.models.Shoe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailsViewModel: ViewModel(){

    private val _productDetailsState = MutableStateFlow(ProductDetailsState())
    val productDetailsState = _productDetailsState.asStateFlow()

    init {
        println("Product details view model")
    }

    fun onEvent(event: ProductDetailsEvent){
        when(event){
            is ProductDetailsEvent.OnAddToCart -> {
                if(_productDetailsState.value.selectedVariation == null) return
                val stock = _productDetailsState.value.selectedVariation?.quantity!!
                val errorMessage = when {
                    stock == 0 -> "This item is out of stock"
                    _productDetailsState.value.quantity > stock -> "You can only buy $stock items"
                    else -> null
                }
                _productDetailsState.update {
                    it.copy(
                        errorMessage = errorMessage
                    )
                }
            }
            is ProductDetailsEvent.OnQuantityChange -> {
                _productDetailsState.update {
                    it.copy(
                        quantity = event.newQuantity
                    )
                }
            }

            is ProductDetailsEvent.OnSizeSelected -> {
                _productDetailsState.update {
                    it.copy(
                        selectedSize = event.size
                    )
                }
                updateShoeVariation(event.size, _productDetailsState.value.selectedColor)
            }

            is ProductDetailsEvent.OnColorSelected -> {
                handleColorSelection(event.color)
            }

            is ProductDetailsEvent.OnImageSelected -> {
                _productDetailsState.update {
                    it.copy(
                        selectedImage = event.image
                    )
                }
            }

            ProductDetailsEvent.OnResetError -> {
                _productDetailsState.update {
                    it.copy(
                        errorMessage = null
                    )
                }
            }
        }
    }

    private fun handleColorSelection(color: String) {
        val sizes = _productDetailsState.value.product?.variants?.filter { it.color == color }?.map { it.size }?.distinct()
        _productDetailsState.update {
            it.copy(
                selectedColor = color,
                sizes = sizes ?: emptyList()
            )
        }
        updateShoeVariation(_productDetailsState.value.selectedSize, color)
    }

    private fun updateShoeVariation(size: Int, color: String){
        if (size == 0 || color == "") return
        val variant = _productDetailsState.value.product?.variants?.find { it.color == color && it.size == size }
        val image = _productDetailsState.value.selectedImage
        _productDetailsState.update {
            it.copy(
                selectedVariation = variant,
                selectedImage = if (variant?.image == null) image else variant.image
            )
        }
    }

    fun resetState() = viewModelScope.launch{
        delay(500)
        _productDetailsState.update {
            ProductDetailsState()
        }
    }


    fun onProductSelected(product: Shoe){
        val colors = product.variants.map { it.color }.distinct()
        val sizes = product.variants.map { it.size }.distinct()
        println("Colors:: $colors, Sizes:: $sizes")
        _productDetailsState.update {
            it.copy(
                product = product,
                colors = colors,
                sizes = sizes,
                selectedColor = if (colors.size == 1) colors.first() else "",
                selectedImage = product.images.first()
            )
        }
    }
}