package features.shop.presentation.screens.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.preferences.SessionHandler
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val sessionHandler: SessionHandler
):ViewModel() {

    fun logout() = viewModelScope.launch {
        sessionHandler.clearSession()
    }

}