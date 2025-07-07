package nl.codingwithlinda.notemark.feature_home.presentation.detail.components.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun TextFieldDialog(
    textTield: @Composable () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier) {

    Dialog(onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false
        ),
    ) {
        Row(
            modifier = modifier
        ) {
            Box(modifier = Modifier
                .weight(1f)
            ) {
                textTield()
            }
            TextButton(
                onClick = { onDismiss() },
                modifier = Modifier
            ) {
                Text("Ready")
            }
        }


    }
}