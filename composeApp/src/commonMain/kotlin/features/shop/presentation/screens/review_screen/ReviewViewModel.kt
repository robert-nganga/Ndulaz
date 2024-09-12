package features.shop.presentation.screens.review_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.preferences.SessionHandler
import features.profile.domain.models.User
import features.shop.domain.models.OrderItem
import features.shop.domain.request.ReviewRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class ReviewViewModel(
    private val sessionHandler: SessionHandler
): ViewModel() {
    private val _reviewScreenState = MutableStateFlow(ReviewScreenState())
    val reviewScreenState = _reviewScreenState.asStateFlow()


    fun onReviewChanged(review: String) {
        println("Review is $review")
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
        println("Review request: $reviewRequest")
        _reviewScreenState.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }
        delay(2500L)
        val isSuccess = Random.nextBoolean()
        if (isSuccess){
            _reviewScreenState.update {
                it.copy(
                    isSubmitted = true,
                    isLoading = false
                )
            }
        } else {
            _reviewScreenState.update {
                it.copy(
                    errorMessage = "Unable to submit review. Try again later",
                    isLoading = false
                )
            }
        }
    }


    private fun createReviewRequest(user: User): ReviewRequest {
        return ReviewRequest(
            rating = _reviewScreenState.value.rating,
            comment = _reviewScreenState.value.review,
            orderItemId = _reviewScreenState.value.orderItem!!.orderId,
            shoeId = _reviewScreenState.value.orderItem!!.shoe.id,
            userId = user.id,
            userName = user.name,
            userImage = user.image
        )
    }
}