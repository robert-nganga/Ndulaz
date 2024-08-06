package features.shop.data

import core.data.database.NdulaDatabase
import core.data.database.toDomain
import core.data.database.toEntity
import core.data.utils.DataResult
import features.shop.domain.models.CartItem
import features.shop.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class CartRepositoryImpl(
    private val database: NdulaDatabase
): CartRepository {

    private val cartDao = database.cartDao()
    override fun getAllCartItems(): Flow<List<CartItem>> = cartDao
        .getAllCartItems()
        .catch {
            it.printStackTrace()
            emit(emptyList())
        }
        .map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun upsertCartItem(cartItem: CartItem): DataResult<String> {
        return try {
            cartDao.upsertCartItem(cartItem.toEntity())
            DataResult.Success("Item added to cart")
        } catch (e: Exception) {
            e.printStackTrace()
            DataResult.Error(message = "Error adding item to cart", exc = e)
        }
    }

    override suspend fun deleteCartItem(id: Int): DataResult<String> {
        return try {
            cartDao.deleteCartItem(id)
            DataResult.Success("Item removed from cart")
        } catch (e: Exception) {
            e.printStackTrace()
            DataResult.Error(message = "Error removing item from cart", exc = e)
        }
    }

    override suspend fun deleteAllCartItems(): DataResult<String> {
        return try {
            cartDao.deleteAllCartItems()
            DataResult.Success("Cart cleared")
        } catch (e: Exception) {
            e.printStackTrace()
            DataResult.Error(message = "Error clearing cart", exc = e)
        }
    }
}