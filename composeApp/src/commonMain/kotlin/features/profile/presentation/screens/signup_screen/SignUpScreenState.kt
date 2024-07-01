package features.profile.presentation.screens.signup_screen

data class SignUpScreenState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val signUpError: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val nameError: String? = null,
    val isLoading: Boolean = false,
    val isSignUpSuccessful: Boolean = false
)
