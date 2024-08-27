package features.shop.data

import com.robert.request.OrderRequest
import core.data.utils.BASE_URL
import core.data.utils.DataResult
import core.data.utils.dataResultSafeApiCall
import features.shop.domain.models.Order
import features.shop.domain.repository.OrderRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class OrderRepositoryImpl(
    private val httpClient: HttpClient
): OrderRepository {
    override suspend fun createOrder(order: OrderRequest): DataResult<Order> = dataResultSafeApiCall {
        val response = httpClient.post("$BASE_URL/orders/add"){
            contentType(ContentType.Application.Json)
            setBody(order)
        }
        response.body<Order>()
    }

    override suspend fun getOrdersForCurrentUser(): DataResult<List<Order>> = dataResultSafeApiCall {
        val response = httpClient.get("$BASE_URL/orders/all")
        response.body<List<Order>>()
    }
}