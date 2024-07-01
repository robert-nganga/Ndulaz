package features.profile.presentation.screens.login_screen

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val authError: String? = null,
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false
)
