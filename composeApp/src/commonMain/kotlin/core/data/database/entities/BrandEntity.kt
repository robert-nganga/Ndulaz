package core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "brands")
data class BrandEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String?,
    val logoUrl: String?,
    val shoes: Int
)
