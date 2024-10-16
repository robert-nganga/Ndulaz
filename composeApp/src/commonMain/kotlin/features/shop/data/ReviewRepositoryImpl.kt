package features.shop.data

import core.data.utils.BASE_URL
import core.data.utils.DataResult
import core.data.utils.dataResultSafeApiCall
import features.shop.domain.models.PaginatedReview
import features.shop.domain.models.Review
import features.shop.domain.models.ReviewFilterOptions
import features.shop.domain.repository.ReviewRepository
import features.shop.domain.request.ReviewRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType

class ReviewRepositoryImpl(
    private val httpClient: HttpClient
): ReviewRepository {
    override suspend fun createReview(reviewRequest: ReviewRequest): DataResult<Review>  = dataResultSafeApiCall {
        val response = httpClient.post("$BASE_URL/reviews/add"){
            contentType(ContentType.Application.Json)
            setBody(reviewRequest)
        }
        response.body<Review>()
    }

    override suspend fun getReviewsForShoe(filterOptions: ReviewFilterOptions, shoeId: Int): DataResult<PaginatedReview> = dataResultSafeApiCall {
        val response = httpClient.get("$BASE_URL/reviews/shoe"){
            parameter("page", filterOptions.page)
            parameter("pageSize", filterOptions.pageSize)
            parameter("rating", filterOptions.rating)

            url {
                appendPathSegments("$shoeId")
            }
        }
        response.body<PaginatedReview>()
    }

    override suspend fun getFeaturedReviews(shoeId: Int): DataResult<List<Review>> = dataResultSafeApiCall {
        val response = httpClient.get("$BASE_URL/reviews/shoe/featured"){
            url {
                appendPathSegments("$shoeId")
            }
        }
        response.body<List<Review>>()
    }
}