package core.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull

object HttpClientFactory {
    fun createHttpClient(tokenProvider: TokenProvider): HttpClient {
        return HttpClient {
            install(ContentNegotiation){
                json()
            }
            install(Auth){
                bearer {
                    loadTokens {
                        val accessToken = tokenProvider.fetch().firstOrNull()
                        BearerTokens(accessToken ?: "", "")
                    }
                    refreshTokens {
                        val refreshToken = tokenProvider.fetch().firstOrNull()
                        BearerTokens(refreshToken ?: "", "")
                    }
                }
            }
        }
    }
}