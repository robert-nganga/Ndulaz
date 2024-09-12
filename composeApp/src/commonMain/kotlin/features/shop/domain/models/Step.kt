package features.shop.domain.models

import org.jetbrains.compose.resources.DrawableResource

data class Step(
    val title: String,
    val description: String,
    val image: DrawableResource
)
