package features.shop.domain.repository

import core.data.database.entities.ShippingAddressEntity
import core.data.utils.DataResult
import features.shop.domain.models.ShippingAddress
import kotlinx.coroutines.flow.Flow

interface ShippingAddressRepository {
    fun getAllAddress(): Flow<List<ShippingAddress>>
    suspend fun upsertAddress(address: ShippingAddress): DataResult<String>
    suspend fun deleteAddress(id: Int): DataResult<String>
    suspend fun deleteAll(): DataResult<String>

}