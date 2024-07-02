package core.data

import kotlinx.coroutines.flow.Flow

interface TokenProvider {
    suspend fun fetch(): Flow<String?>

    suspend fun update(accessToken: String)
}