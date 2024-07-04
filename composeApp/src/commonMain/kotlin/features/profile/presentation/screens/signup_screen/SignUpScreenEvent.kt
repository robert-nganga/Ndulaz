package features.profile.presentation.screens.signup_screen

sealed interface SignUpScreenEvent {
    data class OnNameChanged(val name: String) : SignUpScreenEvent
    data class OnEmailChanged(val email: String) : SignUpScreenEvent
    data class OnPasswordChanged(val password: String) : SignUpScreenEvent
    data object OnSignUpClicked : SignUpScreenEvent

    data object DismissError: SignUpScreenEvent

}