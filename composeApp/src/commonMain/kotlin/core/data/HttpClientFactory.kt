package core.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

object HttpClientFactory {
    fun createHttpClient(authToken: String?): HttpClient {
        return HttpClient {
            install(ContentNegotiation){
                json()
            }
            install(Auth){
                bearer {
                    loadTokens {
                        BearerTokens("", "")
                    }
                }
            }
        }
    }
}