package core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class CategoryEntity(
    val description: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val image: String
)
