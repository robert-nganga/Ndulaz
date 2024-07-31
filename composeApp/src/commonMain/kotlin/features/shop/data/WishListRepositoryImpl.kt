package features.shop.data

import core.data.utils.BASE_URL
import core.data.utils.DataResult
import core.data.utils.dataResultSafeApiCall
import features.shop.domain.models.WishList
import features.shop.domain.repository.WishListRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.appendPathSegments

class WishListRepositoryImpl(
    private val httpClient: HttpClient
): WishListRepository {
    override suspend fun addItemToWishList(shoeId: Int): DataResult<WishList> = dataResultSafeApiCall {
        val response = httpClient.post("$BASE_URL/wishlist/add"){
            url {
                appendPathSegments("$shoeId")
            }
        }
        response.body<WishList>()
    }

    override suspend fun removeItemFromWishList(shoeId: Int): DataResult<WishList> = dataResultSafeApiCall {
        val response = httpClient.delete("$BASE_URL/wishlist/remove"){
            url {
                appendPathSegments("$shoeId")
            }
        }
        response.body<WishList>()
    }

    override suspend fun clearWishList(): DataResult<WishList> = dataResultSafeApiCall {
        val response = httpClient.delete("$BASE_URL/wishlist/clear")
        response.body<WishList>()
    }

    override suspend fun getWishList(): DataResult<WishList> = dataResultSafeApiCall {
        val response = httpClient.get("$BASE_URL/wishlist/my_wishlist")
        response.body<WishList>()
    }
}