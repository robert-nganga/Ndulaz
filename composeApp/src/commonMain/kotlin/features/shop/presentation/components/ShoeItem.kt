package features.shop.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
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
    onWishListClicked: (Shoe) -> Unit,
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
                    .height(140.dp)
                    .background(color = Color.Gray.copy(alpha = 0.4f))
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
                        .background(color = MaterialTheme.colors.primary.copy(alpha = 0.15f))
                        .padding(8.dp)
                ){
                    val stock = shoe.variants.sumOf { it.quantity }
                    Text(
                        "$stock in stock",
                        style = TextStyle(
                            fontSize = 10.sp,
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
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .clip(CircleShape)
                .clickable {
                    onWishListClicked(shoe)
                }
        ){
            Icon(
                if (shoe.isInWishList) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                contentDescription = "",
                modifier = Modifier
                    .padding(6.dp)
                    .size(25.dp),
                tint = Color.Black.copy(
                    alpha = 0.7f
                )
            )
        }
//        WishListIcon(
//            isSelected = false,
//            modifier = Modifier
//                .align(Alignment.TopEnd),
//            onClick = {}
//        )
    }
}

@Composable
fun WishListIcon(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Box(
        modifier = modifier
            .padding(16.dp)
            .clip(CircleShape)
            .background(color = Color.Black.copy(alpha = 0.2f))
            .clickable {
                onClick()
            }

    ){
        Icon(
            if (isSelected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "",
            modifier = modifier
                .padding(6.dp)
                .size(25.dp)
        )
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewShoeItem(
    shoe: Shoe,
    onShoeSelected: (Shoe) -> Unit,
    onWishListClicked: (Shoe) -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .padding(5.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = {
            onShoeSelected(shoe)
        },
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.onSurface.copy(
                alpha = 0.1f
            )
        ),
        elevation = 2.dp
    ){
        Box(
            modifier = Modifier
        ) {
            Column{
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
                        .height(140.dp)
                        .background(color = Color.Gray.copy(alpha = 0.4f))
                )
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                ){
                    Text(
                        shoe.name,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.StarHalf,
                            contentDescription = "",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            "4.6 (20 reviews)",
                            style = TextStyle(
                                color = MaterialTheme.colors.onSurface.copy(
                                    alpha = 0.6f
                                )
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        "Ksh ${shoe.price}",
                        style = TextStyle(
                            fontWeight = FontWeight.W500,
                            lineHeight = 34.sp
                        )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .clip(CircleShape)
                    .clickable {
                        onWishListClicked(shoe)
                    }
            ){
               Icon(
                   if (shoe.isInWishList) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                   contentDescription = "",
                   modifier = Modifier
                       .padding(6.dp)
                       .size(25.dp),
                   tint = Color.Black.copy(
                       alpha = 0.7f
                   )
               )
            }

        }
    }
}