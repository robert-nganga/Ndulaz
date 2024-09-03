package features.shop.presentation.screens.edit_profile_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Camera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import features.profile.domain.models.User
import features.shop.presentation.components.CircularProfilePhoto
import features.shop.presentation.components.CustomOutlinedTextField
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.kenyaflag
import org.jetbrains.compose.resources.painterResource


@Composable
fun EditProfileScreen(
    onNavigateBack: () -> Unit,
    viewModel: EditProfileViewModel,
    currentUser: User
){

    val state by viewModel.editProfileState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.onNameChange(currentUser.name)
        viewModel.onEmailChange(currentUser.email)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Edit Profile")
                },
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                navigationIcon = {
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = onNavigateBack
                    ){
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBackIos,
                            contentDescription = ""
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar (
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 10.dp
                    ),
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colors.background
            ){
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                ){
                    Text(
                        "Save",
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    horizontal = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CircularProfilePhoto(
                photoUrl = currentUser.image,
                onButtonClick = {},
                buttonIcon = Icons.Default.Camera
            )
            Spacer(modifier = Modifier.height(24.dp))
            CustomOutlinedTextField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                label = "Name"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChange,
                label = "Email"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                FlagSection(
                    countryCode = "+254",
                )
                Spacer(modifier = Modifier.width(16.dp))
                CustomOutlinedTextField(
                    value = state.phone,
                    onValueChange = viewModel::onPhoneNumberChange,
                    label = "Phone",
                    modifier = Modifier
                        .weight(1f)
                )
            }

        }
    }
}

@Composable
fun FlagSection(
    modifier: Modifier = Modifier,
    countryCode: String
){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.onSurface.copy(
                alpha = 0.5f
            )
        )
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(Res.drawable.kenyaflag),
                contentDescription = "",
                modifier = Modifier
                    .padding(12.5.dp)
                    .height(30.dp)
                    .width(40.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = countryCode,
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}