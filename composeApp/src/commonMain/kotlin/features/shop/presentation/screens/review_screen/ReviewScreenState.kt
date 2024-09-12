package features.shop.presentation.screens.review_screen

import features.shop.domain.models.OrderItem

data class ReviewScreenState(
    val review: String = "",
    val rating: Double = 0.0,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isSubmitted: Boolean = false,
    val orderItem: OrderItem? = null
)
