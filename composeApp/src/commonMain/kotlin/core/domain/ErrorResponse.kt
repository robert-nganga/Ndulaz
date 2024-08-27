package core.domain

import kotlinx.serialization.Serializable


@Serializable
data class ErrorResponse(
    val message: String?,
    val status: HttpStatus
)

@Serializable
data class HttpStatus(
    val value: Int,
    val description: String
)
