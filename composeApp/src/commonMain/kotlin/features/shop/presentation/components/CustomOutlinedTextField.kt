package features.shop.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    error: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null
){
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            label = {
                Text(label)
            },
            trailingIcon = trailingIcon,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.onSurface,
                unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(
                    alpha = 0.5f
                ),
            ),
            isError = error != null
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (error != null){
            Text(
                error,
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.error
                )
            )
        }
    }
}