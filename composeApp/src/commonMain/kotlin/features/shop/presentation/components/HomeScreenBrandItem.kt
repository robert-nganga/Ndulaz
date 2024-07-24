package features.shop.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Brand
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


@Composable
fun HomeScreenBrandItem(
    brand: Brand,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colors.primary.copy(
                        alpha = 0.12f
                    )
                )
                .clickable { onItemClick() }
        ){
            KamelImage(
                resource = asyncPainterResource(brand.logoUrl ?: ""),
                contentDescription = "${brand.name} logo",
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colors.onBackground
                )
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = brand.name,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground
        )
    }
}