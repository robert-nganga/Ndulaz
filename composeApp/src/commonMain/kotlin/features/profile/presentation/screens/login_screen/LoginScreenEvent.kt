package features.profile.presentation.screens.login_screen

sealed interface LoginScreenEvent {
    data class OnEmailChanged(val email: String): LoginScreenEvent
    data class OnPasswordChange(val password: String): LoginScreenEvent
    data object OnLoginClicked: LoginScreenEvent
}