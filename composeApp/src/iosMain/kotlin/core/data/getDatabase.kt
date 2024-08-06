package core.data

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import core.data.database.NdulaDatabase
import core.data.database.instantiateImpl
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): NdulaDatabase {
    val dbFilePath = NSHomeDirectory() + "/my_room.db"
    return Room.databaseBuilder<NdulaDatabase>(
        name = dbFilePath,
        factory =  { NdulaDatabase::class.instantiateImpl() }
        )
        .setDriver(BundledSQLiteDriver())
        .build()
}