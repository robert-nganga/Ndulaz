package features.shop.presentation.screens.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpCenter
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import features.profile.domain.models.User
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.sample_profile
import org.jetbrains.compose.resources.painterResource


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel
){
    val currentUser by viewModel.currentUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Spacer(modifier = Modifier.height(10.dp))
        currentUser?.let { user ->
            ProfileDetailsSection(
                user = user,
                onEditProfile = {
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        MenuItem(
            icon = Icons.Outlined.Settings,
            title = "Settings",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(10.dp))
        MenuItem(
            icon = Icons.Outlined.ShoppingBag,
            title = "My Orders",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(10.dp))
        MenuItem(
            icon = Icons.Outlined.LocationCity,
            title = "My Address",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(10.dp))
        MenuItem(
            icon = Icons.AutoMirrored.Outlined.HelpCenter,
            title = "Help Center",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(10.dp))
        MenuItem(
            icon = Icons.AutoMirrored.Rounded.Logout,
            title = "Logout",
            onClick = {
                viewModel.logout()
            }
        )
    }
}


@Composable
fun MenuItem(
  modifier: Modifier = Modifier,
  icon: ImageVector,
  title: String,
  onClick: () -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
               onClick()
            }
            .padding(
                horizontal = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
                .padding(
                    vertical = 10.dp
                )
                .size(50.dp)
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colors.onSurface.copy(
                        alpha = 0.1f
                    )
                ),
            contentAlignment = Alignment.Center
        ){
            Icon(
                icon,
                modifier = Modifier
                    .size(28.dp),
                contentDescription = "$title icon"
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            title,
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
fun ProfileDetailsSection(
    modifier: Modifier = Modifier,
    user: User,
    onEditProfile: (User) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
        ){
            Image(
                painter = painterResource(Res.drawable.sample_profile),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
            )
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.colors.surface
                    )
                    .padding(3.dp)
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(
                        color = Color(0xff0377fc)
                    )
                    .clickable { onEditProfile(user) }
                    .align(Alignment.BottomEnd)
            ){
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit profile",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            user.name,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            user.email,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = 0.6f
                )
            )
        )
    }
}