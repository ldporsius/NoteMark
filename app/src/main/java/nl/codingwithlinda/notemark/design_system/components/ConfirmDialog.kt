package nl.codingwithlinda.notemark.design_system.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmText: String,
    modifier: Modifier = Modifier) {


    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        modifier = modifier,
        title = {
            Text(title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Text(message)
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                }
            ) {
                Text(confirmText)
            }

        },
        dismissButton = {
            FilledTonalButton(
                onClick = {
                    onDismiss()
                }

            ) {
                Text("Cancel")
            }
        }
    )
}