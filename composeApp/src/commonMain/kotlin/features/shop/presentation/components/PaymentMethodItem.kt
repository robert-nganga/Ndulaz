package features.shop.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import features.shop.domain.models.PaymentMethod
import org.jetbrains.compose.resources.painterResource


@Composable
fun PaymentMethodItem(
    modifier: Modifier = Modifier,
    paymentMethod: PaymentMethod,
    isSelected: Boolean,
    onClick: () -> Unit
){

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = MaterialTheme.colors.onSurface.copy(
                alpha = 0.2f
            )
        )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp,
                    horizontal = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(paymentMethod.image),
                contentDescription = "${paymentMethod.name} logo",
                modifier = Modifier
                    .size(30.dp),
                colorFilter = if (paymentMethod.name == "Card" || paymentMethod.name == "Cash On Delivery")
                    ColorFilter.tint(MaterialTheme.colors.onSurface) else null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = paymentMethod.name,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.weight(1f))
            RadioButton(
                selected = isSelected,
                onClick = onClick,
            )
        }
    }
}