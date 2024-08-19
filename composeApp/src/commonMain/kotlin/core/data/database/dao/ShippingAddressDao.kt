package core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import core.data.database.entities.ShippingAddressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShippingAddressDao {
    @Query("SELECT * FROM shipping_address")
    fun getAllAddress(): Flow<List<ShippingAddressEntity>>
    @Upsert
    suspend fun upsertAddress(address: ShippingAddressEntity)
    @Query("DELETE FROM shipping_address WHERE id = :id")
    suspend fun deleteAddress(id: Int)
    @Query("DELETE FROM shipping_address")
    suspend fun deleteAll()

}