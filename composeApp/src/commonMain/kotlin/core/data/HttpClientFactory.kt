package core.data

import core.data.preferences.SessionHandler
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
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
                }
            }
        }
    }
}