package core.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import core.data.database.dao.CartDao
import core.data.database.dao.ShippingAddressDao
import core.data.database.entities.CartItemEntity
import core.data.database.entities.ShippingAddressEntity


@Database(
    entities = [CartItemEntity::class, ShippingAddressEntity::class],
    version = 1,
    exportSchema = false
)
@ConstructedBy(NdulaDatabaseConstructor::class)
abstract class NdulaDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun shippingAddressDao(): ShippingAddressDao

}

