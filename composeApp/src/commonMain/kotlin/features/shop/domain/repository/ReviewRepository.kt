package features.shop.domain.repository

import core.data.utils.DataResult
import features.shop.domain.models.PaginatedReview
import features.shop.domain.models.Review
import features.shop.domain.request.ReviewRequest

interface ReviewRepository {
    suspend fun createReview(reviewRequest: ReviewRequest): DataResult<Review>
    suspend fun getReviewsForShoe(page: Int, limit: Int, shoeId: Int): DataResult<PaginatedReview>
    suspend fun getFeaturedReviews(shoeId: Int): DataResult<List<Review>>
}