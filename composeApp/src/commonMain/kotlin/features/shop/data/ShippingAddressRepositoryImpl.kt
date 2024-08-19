package features.shop.data

import core.data.database.NdulaDatabase
import core.data.database.entities.ShippingAddressEntity
import core.data.database.toDomain
import core.data.database.toEntity
import core.data.utils.DataResult
import features.shop.domain.models.ShippingAddress
import features.shop.domain.repository.ShippingAddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShippingAddressRepositoryImpl(
    private val database: NdulaDatabase
): ShippingAddressRepository {

    private val shippingAddressDao = database.shippingAddressDao()

    override fun getAllAddress(): Flow<List<ShippingAddress>> =
        shippingAddressDao
            .getAllAddress()
            .map { addresses ->
                addresses.map { it.toDomain()}
            }

    override suspend fun upsertAddress(address: ShippingAddress): DataResult<String> {
        return try {
            shippingAddressDao.upsertAddress(address.toEntity())
            DataResult.Success("Address added successfully")
        } catch (e: Exception) {
            DataResult.Error("Error adding address")
        }
    }

    override suspend fun deleteAddress(id: Int): DataResult<String> {
        return try {
            shippingAddressDao.deleteAddress(id)
            DataResult.Success("Address deleted successfully")
        } catch (e: Exception) {
            DataResult.Error("Error deleting address")
        }
    }

    override suspend fun deleteAll(): DataResult<String> {
        return try {
            shippingAddressDao.deleteAll()
            DataResult.Success("All addresses deleted successfully")
        } catch (e: Exception) {
            DataResult.Error("Error deleting all addresses")
        }
    }
}