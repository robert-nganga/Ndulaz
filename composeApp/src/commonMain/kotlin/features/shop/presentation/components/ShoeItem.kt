package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import features.shop.domain.models.Shoe
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ShoeItem(
    shoe: Shoe,
    onShoeSelected: (Shoe) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(2.5.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onShoeSelected(shoe)
            }
    ){
        Column(
            modifier = Modifier.padding(10.dp)
        ){
            KamelImage(
                asyncPainterResource(shoe.images.first()),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                onLoading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray.copy(alpha = 0.2f)),
                    )
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .height(160.dp)
                    .background(color = Color.Gray.copy(alpha = 0.8f))
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                shoe.name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.StarHalf,
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    "4.6",
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    "|",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(color = Color.Black.copy(alpha = 0.15f))
                        .padding(8.dp)
                ){
                    val stock = shoe.sizes.sumOf { it.quantity }
                    Text(
                        "$stock in stock",
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = Color.Black,
                            letterSpacing = 0.2.sp
                        )
                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Ksh ${shoe.price}",
                style = TextStyle(
                    fontWeight = FontWeight.W700
                )
            )
        }
    }
}