package features.shop.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.flexible.bottomsheet.material.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetState
import features.shop.domain.models.PaymentMethod


@Composable
fun PaymentMethodsBottomSheet(
    modifier: Modifier = Modifier,
    paymentMethods: List<PaymentMethod>,
    selectedPaymentMethod: PaymentMethod,
    onPaymentMethodSelected: (PaymentMethod) -> Unit,
    sheetState: FlexibleSheetState,
    onDismiss: () -> Unit
){

    FlexibleBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
        containerColor = MaterialTheme.colors.surface,
        scrimColor = MaterialTheme.colors.onSurface.copy(
            alpha = 0.2f
        ),
    ){
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp
                )
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    "Select Payment Method",
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = onDismiss
                ){
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Dismiss"
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            paymentMethods.forEach { paymentMethod ->
                PaymentMethodItem(
                    paymentMethod = paymentMethod,
                    onClick = {
                        onPaymentMethodSelected(paymentMethod)
                    },
                    isSelected = paymentMethod == selectedPaymentMethod
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}