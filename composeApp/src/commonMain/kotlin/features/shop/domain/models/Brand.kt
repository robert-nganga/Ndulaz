package features.shop.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class Brand(
    val id: Int,
    val name: String,
    val description: String?,
    val logoUrl: String?
)