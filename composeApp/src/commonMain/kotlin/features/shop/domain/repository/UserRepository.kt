package features.shop.domain.repository

import core.data.utils.DataResult
import features.profile.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun updateUser(newUser: User): DataResult<User>
    suspend fun logout()
}