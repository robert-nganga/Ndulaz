package features.shop.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.sample_profile
import org.jetbrains.compose.resources.painterResource


@Composable
fun CircularProfilePhoto(
    modifier: Modifier = Modifier,
    photoUrl: String?,
    photoSize: Dp = 160.dp,
    onButtonClick: () -> Unit,
    buttonIcon: ImageVector = Icons.Default.Edit,
){
    Box(
        modifier = modifier
    ){
        if (photoUrl.isNullOrEmpty()){
            Image(
                painter = painterResource(Res.drawable.sample_profile),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(photoSize)
                    .clip(CircleShape)
            )
        } else {

            KamelImage(
                asyncPainterResource(photoUrl),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(photoSize)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
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
                .clickable { onButtonClick() }
                .align(Alignment.BottomEnd)
        ){
            Icon(
                buttonIcon,
                contentDescription = "Edit profile",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}