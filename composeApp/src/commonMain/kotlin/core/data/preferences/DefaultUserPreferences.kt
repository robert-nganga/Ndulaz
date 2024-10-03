package core.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import features.shop.domain.models.UserPreferences
import features.shop.presentation.screens.settings_screen.ThemeSelection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
 class DefaultUserPreferencesRepository (
    private val dataStore: DataStore<Preferences>,
): UserPreferencesRepository {
    override fun getUserPreferences(): Flow<UserPreferences?> = dataStore.data
        .catch {
            emptyPreferences()
        }.map { preferences ->
            preferences[PreferenceKeys.THEME]?.let { theme ->
                UserPreferences(
                    appTheme = ThemeSelection.valueOf(theme)
                )
            }
        }
    override suspend fun changeAppTheme(appTheme: ThemeSelection) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.THEME] = appTheme.name
        }
    }

}