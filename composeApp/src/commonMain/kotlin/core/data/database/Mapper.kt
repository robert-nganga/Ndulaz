package core.data.database

import core.data.database.entities.CartItemEntity
import core.data.database.entities.ShippingAddressEntity
import features.shop.domain.models.CartItem
import features.shop.domain.models.ShippingAddress

data class ShippingAddress(
    val id: Int,
    val name: String,
    val formattedAddress: String,
    val lat: Double,
    val lng: Double,
    val placeId: String,
    val phoneNumber: String,
    val buildingNumber: String,
    val floorNumber: String,
    val doorNumber: String,
    val buildingName: String,
)

fun CartItemEntity.toDomain(): CartItem{
    return CartItem(
        id = id,
        name = name,
        price = price,
        color = color,
        quantity = quantity,
        imageUrl = imageUrl,
        brand = brand,
        shoeId = shoeId,
        variationId = variationId,
        size = size
    )
}

fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        id = id,
        name = name,
        price = price,
        color = color,
        quantity = quantity,
        imageUrl = imageUrl,
        brand = brand,
        shoeId = shoeId,
        variationId = variationId,
        size = size
    )
}

fun ShippingAddressEntity.toDomain(): ShippingAddress{
    return ShippingAddress(
        id = id,
        name = name,
        formattedAddress = formattedAddress,
        placeId = placeId,
        lat = lat,
        lng = lng,
        phoneNumber = phoneNumber,
        floorNumber = floorNumber,
        doorNumber = doorNumber,
        buildingName = buildingName,
    )
}

fun ShippingAddress.toEntity(): ShippingAddressEntity {
    return ShippingAddressEntity(
        id = id,
        name = name,
        formattedAddress = formattedAddress,
        placeId = placeId,
        lat = lat,
        lng = lng,
        phoneNumber = phoneNumber,
        floorNumber = floorNumber,
        doorNumber = doorNumber,
        buildingName = buildingName,
    )
}

