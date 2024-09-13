package features.shop.presentation.screens.review_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.preferences.SessionHandler
import core.data.utils.DataResult
import features.profile.domain.models.User
import features.shop.domain.models.OrderItem
import features.shop.domain.repository.ReviewRepository
import features.shop.domain.request.ReviewRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val sessionHandler: SessionHandler,
    private val reviewRepository: ReviewRepository
): ViewModel() {


    private val _reviewScreenState = MutableStateFlow(ReviewScreenState())
    val reviewScreenState = _reviewScreenState.asStateFlow()


    fun onReviewChanged(review: String) {
        _reviewScreenState.update {
            it.copy(
                review = review
            )
        }
    }

    fun onRatingChanged(rating: Double) {
        _reviewScreenState.update {
            it.copy(
                rating = rating
            )
        }
    }

    fun updateOrderItem(orderItem: OrderItem) {
        _reviewScreenState.update {
            it.copy(
                orderItem = orderItem
            )
        }
    }

    fun resetReviewScreenState(){
        _reviewScreenState.value = ReviewScreenState()
    }

    fun resetErrorMessage(){
        _reviewScreenState.update {
            it.copy(
                errorMessage = null
            )
        }
    }

    fun onSubmitReview() = viewModelScope.launch {
        val currentUser = sessionHandler.getUser().first()
        if (currentUser == null){
            _reviewScreenState.update {
                it.copy(
                    errorMessage = "Please login to submit a review"
                )
            }
            return@launch
        }
        val reviewRequest = createReviewRequest(currentUser)
        _reviewScreenState.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }
        val result = reviewRepository.createReview(reviewRequest)
        when(result){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _reviewScreenState.update {
                    it.copy(
                        errorMessage = "Unable to submit review. Try again later",
                        isLoading = false
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _reviewScreenState.update {
                    it.copy(
                        isSubmitted = true,
                        isLoading = false
                    )
                }
            }
        }
    }


    private fun createReviewRequest(user: User): ReviewRequest {
        return ReviewRequest(
            rating = _reviewScreenState.value.rating,
            comment = _reviewScreenState.value.review,
            orderItemId = _reviewScreenState.value.orderItem!!.id,
            shoeId = _reviewScreenState.value.orderItem!!.shoe.id,
            userId = user.id,
            userName = user.name,
            userImage = user.image
        )
    }
}