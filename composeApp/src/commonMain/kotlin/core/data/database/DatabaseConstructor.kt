package core.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object NdulaDatabaseConstructor : RoomDatabaseConstructor<NdulaDatabase> {
    override fun initialize(): NdulaDatabase
}