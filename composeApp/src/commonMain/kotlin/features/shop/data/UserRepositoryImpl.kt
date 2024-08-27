package features.shop.data

import core.data.preferences.SessionHandler
import core.data.utils.DataResult
import features.profile.domain.models.User
import features.shop.domain.repository.UserRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class UserRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionHandler: SessionHandler
): UserRepository {
    override fun getCurrentUser(): Flow<User?> =
        sessionHandler
            .getUser()
            .catch {
                it.printStackTrace()
                emit(null)
            }

    override suspend fun updateUser(newUser: User): DataResult<User> {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        sessionHandler.clearSession()
    }
}