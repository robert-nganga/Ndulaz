package core.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import core.data.database.dao.BrandDao
import core.data.database.dao.CartDao
import core.data.database.dao.CategoryDao
import core.data.database.dao.ShippingAddressDao
import core.data.database.entities.BrandEntity
import core.data.database.entities.CartItemEntity
import core.data.database.entities.CategoryEntity
import core.data.database.entities.ShippingAddressEntity


@Database(
    entities = [
        CartItemEntity::class,
        ShippingAddressEntity::class,
        BrandEntity::class,
        CategoryEntity::class
    ],
    version = 2,
    exportSchema = false
)
@ConstructedBy(NdulaDatabaseConstructor::class)
abstract class NdulaDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun shippingAddressDao(): ShippingAddressDao
    abstract fun brandDao(): BrandDao
    abstract fun categoryDao(): CategoryDao

}

