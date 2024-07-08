package core.data

import core.data.preferences.SessionHandler
import core.domain.ErrorResponse
import core.domain.exceptions.ConflictException
import core.domain.exceptions.ForbiddenException
import core.domain.exceptions.UnauthorizedException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull

object HttpClientFactory {
    fun createHttpClient(sessionHandler: SessionHandler): HttpClient {
        return HttpClient {
            expectSuccess = true
            install(ContentNegotiation){
                json()
            }
            install(Auth){
                bearer {
                    loadTokens {
                        sessionHandler.getUser().firstOrNull()?.let { user ->
                            BearerTokens(user.token, "")
                        } ?: BearerTokens("", "")
                    }
                    refreshTokens {
                        sessionHandler.getUser().firstOrNull()?.let { user ->
                            BearerTokens(user.token, "")
                        } ?: BearerTokens("", "")
                    }
                    sendWithoutRequest {
                        true
                    }
                }
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    val clientException = exception as? ClientRequestException ?: return@handleResponseExceptionWithRequest
                    val exceptionResponse = clientException.response
                    if (exceptionResponse.status == HttpStatusCode.Unauthorized) {
                        sessionHandler.clearSession()
                        throw UnauthorizedException()
                    }
                    if (exceptionResponse.status == HttpStatusCode.Conflict){
                        throw ConflictException(exceptionResponse.errorMessage())
                    }

                    if (exceptionResponse.status == HttpStatusCode.Forbidden){
                        throw ForbiddenException(exceptionResponse.errorMessage())
                    }
                }
            }
        }
    }

    private suspend fun HttpResponse.errorMessage(): String? {
        return try {
            body<ErrorResponse>().message
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}


