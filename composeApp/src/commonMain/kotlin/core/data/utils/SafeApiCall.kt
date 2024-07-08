package core.data.utils


import core.domain.exceptions.ConflictException
import core.domain.exceptions.ForbiddenException
import core.domain.exceptions.UnauthorizedException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ServerResponseException


suspend fun <T : Any> dataResultSafeApiCall(
    apiCall: suspend () -> T
): DataResult<T> = try {
    DataResult.Success(apiCall.invoke())
} catch (exception: Exception) { println("API error ${exception.message}")
   exception.printStackTrace()
    //Timber.e(throwable)
    when (exception) {
        is ServerResponseException, is NoTransformationFoundException -> {
            DataResult.Error("Server error", exc = exception)
        }

        is ConnectTimeoutException -> {
            DataResult.Error("Network error", exc = exception, networkError = true)
        }

        is UnauthorizedException -> {
            DataResult.Error("Unauthorized", exc = exception)
        }

        is ForbiddenException -> {
            DataResult.Error(exception.message?: "Forbidden", exc = exception)
        }

        is ConflictException -> {
            DataResult.Error(exception.message ?: "Conflict", exc = exception)
        }

        else -> {
            DataResult.Error("Client error", exc = exception)
        }
    }
}