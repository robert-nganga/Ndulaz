package features.profile.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.preferences.SessionHandler
import core.data.utils.DataResult
import core.domain.InputValidation
import domain.requests.SignInRequest
import domain.requests.SignUpRequest
import features.profile.domain.repositories.AuthRepository
import features.profile.domain.utils.errorMessage
import features.profile.presentation.screens.login_screen.LoginScreenEvent
import features.profile.presentation.screens.login_screen.LoginScreenState
import features.profile.presentation.screens.signup_screen.SignUpScreenEvent
import features.profile.presentation.screens.signup_screen.SignUpScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


enum class AuthStatus{
    LoggedIn,
    Loading,
    LoggedOut
}

class AuthViewModel(
    private val inputValidation: InputValidation,
    private val authRepository: AuthRepository,
    private val sessionHandler: SessionHandler
): ViewModel() {

    val isLoggedIn = sessionHandler
        .getUser()
        .map { user->
            if (user != null){
                println("flow logged in")
                AuthStatus.LoggedIn
            } else{
                println("flow logged out")
                AuthStatus.LoggedOut
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = AuthStatus.Loading,
            started = SharingStarted.WhileSubscribed()
        )


    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState: StateFlow<LoginScreenState> = _loginScreenState.asStateFlow()


    private val _signUpScreenState = MutableStateFlow(SignUpScreenState())
    val signUpScreenState: StateFlow<SignUpScreenState> = _signUpScreenState.asStateFlow()


    fun onSignUpScreenEvent(event: SignUpScreenEvent){
        when(event){
            is SignUpScreenEvent.OnEmailChanged -> {
                _signUpScreenState.value = _signUpScreenState.value.copy(email = event.email)
            }
            is SignUpScreenEvent.OnPasswordChanged -> {
                _signUpScreenState.value = _signUpScreenState.value.copy(password = event.password)
            }
            is SignUpScreenEvent.OnNameChanged -> {
                _signUpScreenState.value = _signUpScreenState.value.copy(name = event.name)
            }
            is SignUpScreenEvent.OnSignUpClicked -> {
                if (isSignUpFormValid()) {
                    signUp()
                }
            }
            is SignUpScreenEvent.DismissError -> {
                _signUpScreenState.value = _signUpScreenState.value.copy(signUpError = null)
            }
        }
    }

    fun onLoginScreenEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnEmailChanged -> {
                _loginScreenState.value = _loginScreenState.value.copy(email = event.email)
            }
            is LoginScreenEvent.OnPasswordChange -> {
                _loginScreenState.value = _loginScreenState.value.copy(password = event.password)
            }
            is LoginScreenEvent.OnLoginClicked -> {
                if (isLoginFormValid()) {
                    login()
                }
            }
            is LoginScreenEvent.DismissError -> {
                _loginScreenState.value = _loginScreenState.value.copy(authError = null)
            }
        }
    }

    fun resetLoginState() = viewModelScope.launch {
        delay(300)
        _loginScreenState.update {
            LoginScreenState()
        }
    }

    fun resetSignUpState() = viewModelScope.launch {
        delay(300)
        _signUpScreenState.update {
            SignUpScreenState()
        }
    }

    private fun isLoginFormValid(): Boolean {
        val emailValidation = inputValidation.validateEmail(_loginScreenState.value.email)
        val passwordValidation = inputValidation.validatePassword(_loginScreenState.value.password)
        _loginScreenState.update {
            it.copy(
                emailError = emailValidation,
                passwordError = passwordValidation
            )
        }
        return emailValidation == null && passwordValidation == null
    }

    private fun isSignUpFormValid(): Boolean {
        val emailValidation = inputValidation.validateEmail(_signUpScreenState.value.email)
        val passwordValidation = inputValidation.validatePassword(_signUpScreenState.value.password)
        val nameValidation = inputValidation.validateName(_signUpScreenState.value.name)
        _signUpScreenState.update {
            it.copy(
                emailError = emailValidation,
                passwordError = passwordValidation,
                nameError = nameValidation
            )
        }
        return emailValidation == null && passwordValidation == null && nameValidation == null
    }


    private fun login() = viewModelScope.launch{
        println("login clicked")
        _loginScreenState.update {
            it.copy(
                isLoading = true,
                authError = null
            )
        }
        val signInRequest = SignInRequest(
            email = _loginScreenState.value.email,
            password = _loginScreenState.value.password
        )
        println("Perform sign in$signInRequest")
        when(val response = authRepository.signIn(signInRequest)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _loginScreenState.update {
                    it.copy(
                        authError = response.exc?.errorMessage() ?: "Unknown error occurred",
                        isLoading = false
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _loginScreenState.update {
                    it.copy(
                        isLoading = false,
                        isLoginSuccessful = true
                    )
                }
            }
        }
    }

    private fun signUp() = viewModelScope.launch {
        _signUpScreenState.update {
            it.copy(
                isLoading = true,
                signUpError = null
            )
        }
        val signUpRequest = SignUpRequest(
            name = _signUpScreenState.value.name,
            email = _signUpScreenState.value.email,
            password = _signUpScreenState.value.password,
            image = ""
        )
        when(val response = authRepository.signUp(signUpRequest)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _signUpScreenState.update {
                    it.copy(
                        signUpError = response.exc?.errorMessage() ?: "Unknown error occurred",
                        isLoading = false
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _signUpScreenState.update {
                    it.copy(
                        isSignUpSuccessful = true,
                        isLoading = false
                    )
                }
            }
        }
    }
}