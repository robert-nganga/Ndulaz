package core.data.preferences

import features.shop.domain.models.UserPreferences
import features.shop.presentation.screens.settings_screen.ThemeSelection
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    fun getUserPreferences(): Flow<UserPreferences?>
    suspend fun changeAppTheme(appTheme: ThemeSelection)
}