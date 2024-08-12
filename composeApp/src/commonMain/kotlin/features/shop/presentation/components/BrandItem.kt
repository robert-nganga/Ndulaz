package features.shop.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Brand
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BrandItem(
    modifier: Modifier = Modifier,
    brand: Brand,
    onBrandClick: (Brand) -> Unit,
    imageSize: Dp = 60.dp,
    horizontalPadding: Dp = 26.dp
){
    Card(
        onClick = {
          onBrandClick(brand)
        },
        modifier = modifier.padding(5.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.onSurface.copy(
                alpha = 0.12f
            ),
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = horizontalPadding,
                    vertical = 24.dp
                )
        ){
            brand.logoUrl?.let {
                KamelImage(
                    resource = asyncPainterResource(brand.logoUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSize)
                        .padding(end = 12.dp),
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colors.onBackground
                    )
                )
            }
            Column {
                Text(
                    text = brand.name,
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${brand.shoes} Shoes",
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.onSurface.copy(
                            alpha = 0.4f
                        )
                    )
                )
            }
        }
    }

}