package core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import core.data.database.dao.CartDao
import core.data.database.entities.CartItemEntity


@Database(entities = [CartItemEntity::class], version = 1, exportSchema = false)
abstract class NdulaDatabase: RoomDatabase(), DB {
    abstract fun cartDao(): CartDao

    override fun clearAllTables() {
        super.clearAllTables()
    }

}

// FIXME: Added a hack to resolve below issue:
// Class 'AppDatabase_Impl' is not abstract and does not implement abstract base class member 'clearAllTables'.
interface DB {
    fun clearAllTables() {}
}