package features.shop.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import features.shop.domain.models.Review
import features.shop.presentation.utils.randomColor
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    review: Review,
){
    Column(
        modifier = modifier
    ){
        Row {
            UserImage(
                image = review.userImage,
                name = review.userName
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = review.userName,
                    style = MaterialTheme.typography.body2.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Ratings(
                    rating = review.rating.toInt(),
                    starSize = 18.dp,
                    spacing = 0.dp,
                    starColor = MaterialTheme.colors.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = review.comment,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun UserImage(
    modifier: Modifier = Modifier,
    image: String?,
    name: String,
) {
    val backGroundColor by remember {
        mutableStateOf(randomColor())
    }

    if (image.isNullOrEmpty()) {
        Box(
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(backGroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.first().toString(),
                style = MaterialTheme.typography.h5.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
            )
        }
    } else {
        KamelImage(
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape),
            resource = asyncPainterResource(image),
            contentScale = ContentScale.Crop,
            contentDescription = "User Image",
        )
    }
}