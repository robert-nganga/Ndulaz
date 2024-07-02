package features.profile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val cartId: Int,
    val created: String,
    val email: String,
    val id: Int,
    val image: String,
    val name: String,
    val token: String
)