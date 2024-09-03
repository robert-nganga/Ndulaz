package features.shop.domain.repository

import com.robert.request.OrderRequest
import core.data.utils.DataResult
import features.shop.domain.models.Order

interface OrderRepository {
    suspend fun createOrder(order: OrderRequest): DataResult<Order>
    suspend fun getOrdersForCurrentUser(): DataResult<List<Order>>
    suspend fun getActiveOrders(): DataResult<List<Order>>
    suspend fun getCompletedOrders(): DataResult<List<Order>>

}