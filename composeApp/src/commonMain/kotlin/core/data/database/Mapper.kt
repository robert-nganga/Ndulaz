package core.data.database

import core.data.database.entities.CartItemEntity
import features.shop.domain.models.CartItem

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

