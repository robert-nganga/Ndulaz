package core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import core.data.database.entities.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Upsert
    suspend fun upsertCartItem(cartItem: CartItemEntity)

    @Query("SELECT * FROM cart")
    fun getAllCartItems(): Flow<List<CartItemEntity>>

    @Query("DELETE FROM cart WHERE id = :id")
    suspend fun deleteCartItem(id: Int)

    @Query("DELETE FROM cart")
    suspend fun deleteAllCartItems()

}