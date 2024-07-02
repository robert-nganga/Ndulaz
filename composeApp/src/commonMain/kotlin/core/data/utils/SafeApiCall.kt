package core.data.utils


import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ServerResponseException


suspend fun <T : Any> dataResultSafeApiCall(
    apiCall: suspend () -> T
): DataResult<T> = try {
    DataResult.Success(apiCall.invoke())
} catch (throwable: Throwable) {
    //Timber.e(throwable)
    when (throwable) {
        is ServerResponseException, is NoTransformationFoundException -> {
            DataResult.Error("Server error", exc = throwable)
        }

        is ConnectTimeoutException -> {
            DataResult.Error("Network error", exc = throwable, networkError = true)
        }

        else -> {
            DataResult.Error("Client error", exc = throwable)
        }
    }
}