package nl.codingwithlinda.notemark.feature_home.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailAction

@Composable
fun NoteDetailTopBar(
    onAction: (NoteDetailAction) -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                onAction(NoteDetailAction.ConfirmCancelDialog)
            }
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }

        TextButton(
            onClick = {
                onAction(NoteDetailAction.SaveAction)

            }
        ) {
            Text(text = "Save note".uppercase(),
                color = MaterialTheme.colorScheme.primary)
        }

    }
}