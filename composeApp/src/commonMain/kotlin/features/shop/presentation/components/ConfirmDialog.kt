package features.shop.presentation.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    confirmButtonText: String,
    dismissButtonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                title,
                style = MaterialTheme.typography.h6
            )
        },
        text = {
           Text(
               message,
               style = MaterialTheme.typography.body1
           )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ){
                Text(
                    confirmButtonText,
                    style = TextStyle(
                        color = MaterialTheme.colors.onSurface
                    )
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ){
                Text(
                    dismissButtonText,
                    style = TextStyle(
                        color = MaterialTheme.colors.onSurface
                    )
                )
            }
        },
    )
}