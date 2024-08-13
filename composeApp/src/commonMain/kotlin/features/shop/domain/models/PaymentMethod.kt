package features.shop.domain.models

import org.jetbrains.compose.resources.DrawableResource

data class PaymentMethod(
    val name: String,
    val image: DrawableResource,
)
