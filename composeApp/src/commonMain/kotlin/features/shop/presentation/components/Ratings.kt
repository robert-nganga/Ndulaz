package features.shop.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Ratings(
    modifier: Modifier = Modifier,
    rating: Int,
    starSize: Dp,
    starColor: Color,
    spacing: Dp = 5.dp,
    onRatingChanged: ((rating: Double) -> Unit)? = null
){
    Row(
        modifier = modifier.padding(3.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for(x in 1 .. 5){
            Icon(
                imageVector = if (x <= rating) Icons.Rounded.StarRate else Icons.Rounded.StarOutline,
                contentDescription = null,
                modifier = Modifier
                    .size(starSize)
                    .clickable {
                        if (onRatingChanged != null){
                            onRatingChanged(x.toDouble())
                        }
                    },
                tint = starColor
            )
            Spacer(modifier = Modifier.width(spacing))
        }
    }
}