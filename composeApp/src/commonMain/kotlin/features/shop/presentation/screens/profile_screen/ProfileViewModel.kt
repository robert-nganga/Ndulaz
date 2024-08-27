package features.shop.presentation.screens.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import features.shop.domain.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel(){

    val currentUser = userRepository
        .getCurrentUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )


    fun logout() = viewModelScope.launch {
        userRepository.logout()
    }
}