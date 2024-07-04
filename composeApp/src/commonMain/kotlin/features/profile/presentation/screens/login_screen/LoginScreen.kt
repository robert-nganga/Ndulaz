package features.profile.presentation.screens.login_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.presentation.components.MyOutlinedTextField
import core.presentation.components.ProgressDialog
import features.profile.presentation.screens.AuthViewModel
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.dont_have_account
import ndula.composeapp.generated.resources.email
import ndula.composeapp.generated.resources.login
import ndula.composeapp.generated.resources.login_title
import ndula.composeapp.generated.resources.password
import ndula.composeapp.generated.resources.sign_up
import org.jetbrains.compose.resources.stringResource


@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onNavigateToSignUp: () -> Unit,
    onNavigateToHome: () -> Unit
) {

    val uiState by authViewModel.loginScreenState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var obscurePassword by remember {
        mutableStateOf(true)
    }

    // Loading dialog
    if (uiState.isLoading){
        ProgressDialog(
            text = "Please wait",
        )
    }


    LaunchedEffect(uiState.isLoginSuccessful){
        if (uiState.isLoginSuccessful){
            onNavigateToHome()
        }
    }

    LaunchedEffect(uiState.authError){
        if (uiState.authError != null) {
            snackbarHostState.showSnackbar(
                message = uiState.authError!!,
                actionLabel = "Dismiss"
            )
            authViewModel.onLoginScreenEvent(LoginScreenEvent.DismissError)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = {
                    Snackbar(
                        snackbarData = it,
                        backgroundColor = MaterialTheme.colors.error,
                        contentColor = Color.White
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ){
            Text(
                stringResource(Res.string.login_title),
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.1.sp
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            MyOutlinedTextField(
                value = uiState.email,
                onValueChange = {
                    authViewModel.onLoginScreenEvent(LoginScreenEvent.OnEmailChanged(it))
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Email,
                        contentDescription = "Email icon"
                    )
                },
                placeholder = {
                    Text(
                        stringResource(Res.string.email)
                    )
                },
                isError = uiState.emailError != null
            )
            if (uiState.emailError != null){
                Text(
                    uiState.emailError!!,
                    style = TextStyle(
                        color = MaterialTheme.colors.error
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            MyOutlinedTextField(
                value = uiState.password,
                onValueChange = {
                    authViewModel.onLoginScreenEvent(LoginScreenEvent.OnPasswordChange(it))
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Lock,
                        contentDescription = "Password icon"
                    )
                },
                placeholder = {
                    Text(
                        stringResource(Res.string.password)
                    )
                },
                obscureText = obscurePassword,
                trailingIcon = {
                    IconButton(
                        onClick = {obscurePassword = !obscurePassword}
                    ){
                        Icon(
                            if (obscurePassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            "Password icon"
                        )
                    }
                },
                isError = uiState.passwordError != null
            )
            if (uiState.passwordError != null){
                Text(
                    uiState.passwordError!!,
                    style = TextStyle(
                        color = MaterialTheme.colors.error
                    )
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    authViewModel.onLoginScreenEvent(LoginScreenEvent.OnLoginClicked)
                },
                shape = RoundedCornerShape(18.dp)
            ){
                Text(
                    stringResource(Res.string.login),
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(
                    stringResource(Res.string.dont_have_account),
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.width(5.dp))
                TextButton(
                    onClick = onNavigateToSignUp
                ){
                    Text(
                        stringResource(Res.string.sign_up),
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.primary
                        )
                    )
                }
            }

        }
    }

}