package core.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import core.data.TokenProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


private object PreferencesKeys {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
}

class DefaultTokenProvider(
    private val dataStore: DataStore<Preferences>
): TokenProvider {

    override suspend fun fetch(): Flow<String?> = dataStore.data
        .catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[PreferencesKeys.ACCESS_TOKEN]
        }

    override suspend fun update(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ACCESS_TOKEN] = accessToken
        }
    }
}