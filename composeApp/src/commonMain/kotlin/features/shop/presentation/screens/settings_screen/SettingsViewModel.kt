package features.shop.presentation.screens.settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    private val _settingsState = MutableStateFlow(SettingsScreenState())
    val settingsState = _settingsState.asStateFlow()

    val userPreferences = userPreferencesRepository
        .getUserPreferences()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )



    init {
        println("SettingsViewModel init")
    }

    fun updateSelectedTheme(theme: ThemeSelection) {
        _settingsState.update {
            it.copy(
                selectedTheme = theme
            )
        }
    }
    fun updateTheme(theme: ThemeSelection)  = viewModelScope.launch {
        _settingsState.update {
            it.copy(
                selectedTheme = theme
            )
        }

        userPreferencesRepository.changeAppTheme(theme)
    }
}