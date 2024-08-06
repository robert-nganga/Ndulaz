package features.shop.domain.repository

import core.data.utils.DataResult
import features.shop.domain.models.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getAllCartItems(): Flow<List<CartItem>>
    suspend fun upsertCartItem(cartItem: CartItem): DataResult<String>
    suspend fun deleteCartItem(id: Int): DataResult<String>
    suspend fun deleteAllCartItems(): DataResult<String>
}