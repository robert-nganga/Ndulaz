package features.shop.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import features.shop.domain.models.OrderItem
import features.shop.presentation.utils.parseColorFromString
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun CompletedOrderItemDetails(
    modifier: Modifier = Modifier,
    orderItem: OrderItem,
    status: String,
    buttonText: String,
    hasReview: Boolean = false,
    onButtonClick: () -> Unit,
){
    val variant by remember {
        mutableStateOf(
            orderItem.shoe.variants.find { it.id == orderItem.variantId }!!
        )
    }

    Card(
        modifier = modifier
            .padding(
                vertical = 5.dp
            ),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(16.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            KamelImage(
                resource = asyncPainterResource(orderItem.shoe.images.first()),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(16.dp)),
                onLoading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray.copy(alpha = 0.2f)),
                    )
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = orderItem.shoe.name,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(
                        modifier = Modifier
                            .size(15.dp)
                            .background(
                                color = variant.color.parseColorFromString(),
                                shape = RoundedCornerShape(10.dp)
                            )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = variant.color,
                        style = MaterialTheme.typography.body2.copy(
                            color = MaterialTheme.colors.onSurface.copy(
                                alpha = 0.5f
                            )
                        ),
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "|",
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.onSurface.copy(
                                alpha = 0.5f
                            )
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Size = ${variant.size}",
                        style = MaterialTheme.typography.body2.copy(
                            color = MaterialTheme.colors.onSurface.copy(
                                alpha = 0.5f
                            )
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "|",
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.onSurface.copy(
                                alpha = 0.5f
                            )
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Qty = ${orderItem.quantity}",
                        style = MaterialTheme.typography.body2.copy(
                            color = MaterialTheme.colors.onSurface.copy(
                                alpha = 0.5f
                            )
                        )
                    )

                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(color = Color(0xff37ab4a).copy(alpha = 0.1f))
                        .padding(8.dp)
                ){
                    Text(
                        status,
                        style = TextStyle(
                            fontSize = 10.sp,
                            letterSpacing = 0.2.sp,
                            color = Color(0xff37ab4a)
                        )
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        "Ksh ${orderItem.price}",
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.sp
                        )
                    )
                    if(hasReview) {
                        ReviewSection(
                            rating = 4.5f,
                        )
                    }else{
                        Button(
                            onClick = onButtonClick,
                            shape = RoundedCornerShape(10.dp)
                        ){
                            Text(
                                buttonText,
                                style = MaterialTheme.typography.body2.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.sp
                                ),
                                modifier = Modifier.padding(
                                    vertical = 2.dp,
                                    horizontal = 3.dp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewSection(
    modifier: Modifier = Modifier,
    rating: Float,
){
    Box(
        modifier = Modifier
            .padding(end = 10.dp)
            .border(
                width = 1.0.dp,
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = 0.25f
                ),
                shape = RoundedCornerShape(10.dp)
            )
    ){
        Row(
            modifier = modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Icon(
                Icons.Default.Star,
                contentDescription = "",
                tint = Color(0xFFFF9529),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                "$rating/5",
                style = MaterialTheme.typography.body2.copy(
                    letterSpacing = 0.sp,
                )
            )
        }
    }
}