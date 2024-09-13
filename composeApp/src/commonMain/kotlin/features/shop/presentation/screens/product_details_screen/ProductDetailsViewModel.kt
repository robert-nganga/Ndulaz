package features.shop.presentation.screens.product_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.utils.DataResult
import features.shop.domain.models.CartItem
import features.shop.domain.models.Shoe
import features.shop.domain.repository.CartRepository
import features.shop.domain.repository.ReviewRepository
import features.shop.domain.repository.WishListRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val wishListRepository: WishListRepository,
    private val cartRepository: CartRepository,
    private val reviewRepository: ReviewRepository
): ViewModel(){

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
                        snackBarMessage = errorMessage,
                        isError = errorMessage != null
                    )
                }
                if(errorMessage != null) return
                _productDetailsState.update{
                    it.copy(
                        showAddToCartSheet = true,
                        addToCartState = AddToCartState.Idle(_productDetailsState.value.toCartItem())
                    )
                }
            }
            is ProductDetailsEvent.OnQuantityChange -> {
                if(_productDetailsState.value.selectedVariation == null) return
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
                        snackBarMessage = null
                    )
                }
            }

            is ProductDetailsEvent.OnWishListIconClick -> {
                _productDetailsState.value.product?.let {shoe ->
                    if(shoe.isInWishList) removeItemFromWishList(shoe.id)
                    else addItemToWishList(shoe.id)
                }
            }

            is ProductDetailsEvent.OnSaveCartItem -> {
                saveCartItem(event.item)
            }
        }
    }

    fun updateAddToCartSheetVisibility(isVisible: Boolean){
        _productDetailsState.update {
            it.copy(
                showAddToCartSheet = isVisible
            )
        }
    }
    private fun saveCartItem(item: CartItem) = viewModelScope.launch {
        _productDetailsState.update {
            it.copy(
                addToCartState = AddToCartState.Loading
            )
        }
        when(val response = cartRepository.upsertCartItem(item)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _productDetailsState.update {
                    it.copy(
                        addToCartState = AddToCartState.Error(response.message)
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _productDetailsState.update {
                    it.copy(
                        addToCartState = AddToCartState.Success(response.data)
                    )
                }
            }
        }
    }


    private fun getFeaturedReviews(shoeId: Int) = viewModelScope.launch {
        _productDetailsState.update {
            it.copy(
                featuredReviewsState = FeaturedReviewsState.Loading
            )
        }

        val response = reviewRepository.getReviewsForShoe(page = 1, limit = 3, shoeId = shoeId)
        when(response){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _productDetailsState.update {
                    it.copy(
                        featuredReviewsState = FeaturedReviewsState.Error(response.message)
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                val reviews = response.data.reviews
                _productDetailsState.update {
                    it.copy(
                        featuredReviewsState = if(reviews.isEmpty()) FeaturedReviewsState.Empty
                            else FeaturedReviewsState.Success(response.data)
                    )
                }
            }
        }
    }


    private fun addItemToWishList(shoeId: Int) = viewModelScope.launch {
        _productDetailsState.update {
            it.copy(
                isError = false,
            )
        }
        when(val response = wishListRepository.addItemToWishList(shoeId)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _productDetailsState.update {
                    it.copy(
                        snackBarMessage = "Couldn't add item to wish list",
                        isError = true
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _productDetailsState.update {
                    it.copy(
                        snackBarMessage = "Item added to wish list"
                    )
                }
                updateItemWishListStatus()
            }
        }
    }

    private fun removeItemFromWishList(shoeId: Int) = viewModelScope.launch {
        _productDetailsState.update {
            it.copy(
                isError = false,
            )
        }
        when(val response = wishListRepository.removeItemFromWishList(shoeId)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _productDetailsState.update {
                    it.copy(
                        snackBarMessage = "Couldn't remove item from wish list",
                        isError = true
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _productDetailsState.update {
                    it.copy(
                        snackBarMessage = "Removed item from wish list"
                    )
                }
                updateItemWishListStatus()
            }
        }
    }

    private fun updateItemWishListStatus() {
        _productDetailsState.value.product?.let {shoe ->
            _productDetailsState.update {
                it.copy(
                    product = shoe.copy(
                        isInWishList = !shoe.isInWishList
                    )
                )
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
        getFeaturedReviews(shoeId = product.id)
    }
}