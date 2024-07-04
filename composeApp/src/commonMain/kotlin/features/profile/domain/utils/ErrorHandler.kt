package features.profile.domain.utils

import core.domain.exceptions.ConflictException
import core.domain.exceptions.ForbiddenException
import core.domain.exceptions.UnauthorizedException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerializationException


fun Exception.errorMessage(): String {
    return when (this) {
        is ServerResponseException -> "We're experiencing server issues. Please try again later."
        is NoTransformationFoundException -> "There was an issue processing the data. Please try again."
        is ConnectTimeoutException -> "Connection timed out. Please check your internet connection and try again."
        is UnauthorizedException -> "Your session has expired. Please log in again."
        is ForbiddenException -> message ?: "Invalid email or password"
        is ConflictException -> message ?: "Email already exists"
        is SerializationException -> "There was an issue processing the data. Please try again later."
        is ClientRequestException -> {
            when (response.status) {
                HttpStatusCode.BadRequest -> "Invalid request. Please check your input and try again."
                HttpStatusCode.NotFound -> "The requested resource was not found."
                else -> "An unexpected error occurred. Please try again later."
            }
        }
        else -> "An unexpected error occurred. Please try again later."
    }
}