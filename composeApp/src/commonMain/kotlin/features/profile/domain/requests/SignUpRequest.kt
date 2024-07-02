package domain.requests

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val image: String
)
