package core.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import features.profile.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private object PreferenceKeys {
    val USER_JSON = stringPreferencesKey("user_json")
}

class DefaultSessionHandler(
    private val dataStore: DataStore<Preferences>,
    private val json: Json = Json { ignoreUnknownKeys = true }
) : SessionHandler {

    override fun getUser(): Flow<User?> = dataStore.data
        .catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[PreferenceKeys.USER_JSON]?.let { userJson ->
                try {
                    json.decodeFromString<User>(userJson)
                } catch (e: SerializationException) {
                    null
                }
            }
        }

    override suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.USER_JSON] = json.encodeToString(user)
        }
    }

    override suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.USER_JSON)
        }
    }
}