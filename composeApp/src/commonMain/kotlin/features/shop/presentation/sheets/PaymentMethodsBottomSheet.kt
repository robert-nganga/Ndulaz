package features.shop.presentation.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.flexible.bottomsheet.material.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetState
import features.shop.domain.models.PaymentMethod
import features.shop.presentation.components.PaymentMethodItem


@Composable
fun PaymentMethodsBottomSheet(
    modifier: Modifier = Modifier,
    paymentMethods: List<PaymentMethod>,
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
        windowInsets = WindowInsets(left = 0.dp, right = 0.dp, top = 0.dp, bottom = 0.dp)
    ){
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp
                )
        ){
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Select payment method",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            paymentMethods.forEach { paymentMethod ->
                PaymentMethodItem(
                    paymentMethod = paymentMethod,
                    onClick = {
                        onPaymentMethodSelected(paymentMethod)
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}