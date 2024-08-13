package core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "cart",
    indices = [Index(value = ["shoe_id", "variation_id"], unique = true)]
    )
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "item_name") val name: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "shoe_id") val shoeId: Int,
    @ColumnInfo(name = "brand") val brand: String?,
    @ColumnInfo(name = "size") val size: Int,
    @ColumnInfo(name = "Color") val color: String,
    @ColumnInfo(name = "variation_id") val variationId: Int
)
