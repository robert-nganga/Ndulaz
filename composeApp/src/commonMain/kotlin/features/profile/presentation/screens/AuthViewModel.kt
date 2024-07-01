package features.profile.presentation.screens

import androidx.lifecycle.ViewModel
import core.domain.InputValidation
import features.profile.presentation.screens.login_screen.LoginScreenEvent
import features.profile.presentation.screens.login_screen.LoginScreenState
import features.profile.presentation.screens.signup_screen.SignUpScreenEvent
import features.profile.presentation.screens.signup_screen.SignUpScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(
    private val inputValidation: InputValidation
): ViewModel() {

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
                signUp()
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
                login()
            }
        }
    }

    private fun login() {
        TODO("Not yet implemented")
    }

    private fun signUp() {
        TODO("Not yet implemented")
    }
}