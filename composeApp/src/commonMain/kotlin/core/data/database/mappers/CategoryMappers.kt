package core.data.database.mappers

import core.data.database.entities.CategoryEntity
import features.shop.domain.models.Category

fun CategoryEntity.toDomain():Category{
    return Category(
        id = id,
        name = name,
        description = description,
        image = image
    )
}

fun Category.toEntity():CategoryEntity{
    return CategoryEntity(
        id = id,
        name = name,
        description = description,
        image = image
    )
}