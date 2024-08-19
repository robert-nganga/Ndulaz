package core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shipping_address")
data class ShippingAddressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @ColumnInfo(name = "formatted_address")
    val formattedAddress: String,
    val lat: Double,
    val lng: Double,
    @ColumnInfo(name = "place_id")
    val placeId: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    @ColumnInfo(name = "floor_number")
    val floorNumber: String,
    @ColumnInfo(name = "door_number")
    val doorNumber: String,
    @ColumnInfo(name = "building_name")
    val buildingName: String,
)
