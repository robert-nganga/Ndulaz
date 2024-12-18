package core.data.preferences

import features.profile.domain.models.User
import kotlinx.coroutines.flow.Flow

interface SessionHandler {
    fun getUser(): Flow<User?>
    suspend fun saveUser(user: User)
    suspend fun clearSession()
}