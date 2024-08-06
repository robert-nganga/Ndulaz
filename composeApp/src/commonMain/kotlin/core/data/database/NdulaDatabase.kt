package core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import core.data.database.dao.CartDao
import core.data.database.entities.CartItemEntity


@Database(entities = [CartItemEntity::class], version = 1, exportSchema = false)
abstract class NdulaDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao

}