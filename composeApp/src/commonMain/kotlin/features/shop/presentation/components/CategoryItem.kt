package features.shop.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Category

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
){

    Box(
        modifier = modifier
            .border(
                width = 1.5.dp,
                color = MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = if (isSelected) MaterialTheme.colors.onBackground else Color.Transparent
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ){
        Text(
            text = category,
            color = if (isSelected) MaterialTheme.colors.background else MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 6.dp
            )
        )
    }

}