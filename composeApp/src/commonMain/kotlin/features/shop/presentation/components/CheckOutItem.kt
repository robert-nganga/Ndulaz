package features.shop.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import features.shop.domain.models.CartItem
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


@Composable
fun CheckOutItem(
    modifier: Modifier = Modifier,
    cartItem: CartItem,
){
    Card(
        modifier = modifier
            .padding(bottom = 6.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(18.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            KamelImage(
                resource = asyncPainterResource(cartItem.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                cartItem.brand?.let {brand ->
                    BrandLabel(
                        brandName = brand
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Text(
                    cartItem.name,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(10.dp))
                Row {
                    VariationInfo(
                        title = "Color",
                        value = cartItem.color
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    VariationInfo(
                        title = "Size",
                        value = cartItem.size.toString()
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    VariationInfo(
                        title = "Qty",
                        value = cartItem.quantity.toString()
                    )
                    Text(
                        "Ksh ${cartItem.price * cartItem.quantity}",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
            }
        }
    }
}