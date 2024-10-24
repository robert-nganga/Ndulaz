package core.data.database.mappers

import core.data.database.entities.BrandEntity
import features.shop.domain.models.Brand

fun BrandEntity.toDomain(): Brand{
    return Brand(
        id = id,
        name = name,
        description = description,
        logoUrl = logoUrl,
        shoes = shoes
    )
}

fun Brand.toEntity(): BrandEntity{
    return BrandEntity(
        id = id,
        name = name,
        description = description,
        logoUrl = logoUrl,
        shoes = shoes
    )
}