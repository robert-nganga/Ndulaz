package core.data

import core.data.preferences.SessionHandler
import core.domain.ErrorResponse
import core.domain.exceptions.BadRequestException
import core.domain.exceptions.ConflictException
import core.domain.exceptions.ForbiddenException
import core.domain.exceptions.UnauthorizedException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.logging.Logger
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun createHttpClient(sessionHandler: SessionHandler, engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            expectSuccess = true
            install(ContentNegotiation){
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging){
                level = LogLevel.ALL
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
                        !it.url.pathSegments.contains("auth")
                        //true
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

                    if (exceptionResponse.status == HttpStatusCode.BadRequest){
                        throw BadRequestException(exceptionResponse.errorMessage())
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


