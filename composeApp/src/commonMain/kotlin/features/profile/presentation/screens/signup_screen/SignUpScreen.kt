package features.profile.presentation.screens.signup_screen

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
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.presentation.components.MyOutlinedTextField
import core.presentation.components.ProgressDialog
import features.profile.presentation.screens.AuthViewModel
import features.profile.presentation.screens.login_screen.LoginScreenEvent
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.already_have_account
import ndula.composeapp.generated.resources.dont_have_account
import ndula.composeapp.generated.resources.email
import ndula.composeapp.generated.resources.full_name
import ndula.composeapp.generated.resources.login
import ndula.composeapp.generated.resources.login_title
import ndula.composeapp.generated.resources.password
import ndula.composeapp.generated.resources.sign_up
import ndula.composeapp.generated.resources.sign_up_title
import org.jetbrains.compose.resources.stringResource


@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit
) {

    val uiState by authViewModel.signUpScreenState.collectAsState()

    var obscurePassword by remember {
        mutableStateOf(true)
    }

    if (uiState.isLoading){
        ProgressDialog(
            text = "Please wait"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ){
        Text(
            stringResource(Res.string.sign_up_title),
            style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.1.sp
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        MyOutlinedTextField(
            value = uiState.name,
            onValueChange = {
                authViewModel.onSignUpScreenEvent(SignUpScreenEvent.OnNameChanged(it))
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Person,
                    contentDescription = "Person icon"
                )
            },
            placeholder = {
                Text(
                    stringResource(Res.string.full_name)
                )
            },
            isError = uiState.nameError != null
        )
        uiState.nameError?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        MyOutlinedTextField(
            value = uiState.email,
            onValueChange = {
                authViewModel.onSignUpScreenEvent(SignUpScreenEvent.OnEmailChanged(it))
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
        uiState.emailError?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        MyOutlinedTextField(
            value = uiState.password,
            onValueChange = {
                authViewModel.onSignUpScreenEvent(SignUpScreenEvent.OnPasswordChanged(it))
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
        uiState.passwordError?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                  authViewModel.onSignUpScreenEvent(SignUpScreenEvent.OnSignUpClicked)
                println("Button clicked")
            },
            shape = RoundedCornerShape(18.dp)
        ){
            Text(
                stringResource(Res.string.sign_up),
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
                stringResource(Res.string.already_have_account),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.width(5.dp))
            TextButton(
                onClick = onNavigateToLogin
            ){
                Text(
                    stringResource(Res.string.login),
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                )
            }
        }


    }
}