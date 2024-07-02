package features.profile.data

import core.data.preferences.DefaultSessionHandler
import core.data.utils.BASE_URL
import core.data.utils.DataResult
import core.data.utils.dataResultSafeApiCall
import domain.requests.SignInRequest
import domain.requests.SignUpRequest
import features.profile.domain.models.User
import features.profile.domain.repositories.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionHandler: DefaultSessionHandler
): AuthRepository {
    override suspend fun signIn(signInRequest: SignInRequest): DataResult<User> {
        return dataResultSafeApiCall {
            val response = httpClient.post("$BASE_URL/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(signInRequest)
            }
            val user = response.body<User>()
            sessionHandler.saveUser(user)
            user
        }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): DataResult<User> {
        return dataResultSafeApiCall {
            val response = httpClient.post("$BASE_URL/auth/signup") {
                contentType(ContentType.Application.Json)
                setBody(signUpRequest)
            }
            val user = response.body<User>()
            sessionHandler.saveUser(user)
            user
        }
    }

    override suspend fun signOut() {
        sessionHandler.clearSession()
    }
}