package features.shop.presentation.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.EditLocation
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.flexible.bottomsheet.material.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetState
import features.shop.domain.models.ShippingAddress

@Composable
fun ShippingAddressBottomSheet(
    modifier: Modifier = Modifier,
    shippingAddresses: List<ShippingAddress>,
    selectedShippingAddress: ShippingAddress?,
    onShippingAddressSelected: (ShippingAddress) -> Unit,
    onAddShippingAddress: () -> Unit,
    sheetState: FlexibleSheetState,
    onEditClick: (ShippingAddress) -> Unit,
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
                .padding(horizontal = 16.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    "Select shipping address",
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
            shippingAddresses.forEach { shippingAddress ->
                ShippingAddressSection(
                    shippingAddress = shippingAddress,
                    selected = shippingAddress == selectedShippingAddress,
                    onClick = {
                        onShippingAddressSelected(shippingAddress)
                    },
                    onEditClick = {
                        onEditClick(shippingAddress)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Divider(
                    modifier = Modifier
                        .padding(
                            horizontal = 32.dp
                        ),
                    color = MaterialTheme.colors.onSurface.copy(
                        alpha = 0.3f
                    ),
                    thickness = 0.8.dp
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onAddShippingAddress,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(
                    "Add new address",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun ShippingAddressSection(
    modifier: Modifier = Modifier,
    shippingAddress: ShippingAddress,
    selected: Boolean,
    onClick: () -> Unit,
    onEditClick: () -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(
            selected = selected,
            onClick = onClick,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                shippingAddress.name,
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                shippingAddress.formattedAddress,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.Phone,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    shippingAddress.phoneNumber,
                    style = MaterialTheme.typography.body2
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(
            onClick = onEditClick
        ){
            Icon(
                Icons.Outlined.EditLocation,
                contentDescription = ""
            )
        }
    }
}