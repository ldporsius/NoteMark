package nl.codingwithlinda.notemark.feature_home.presentation.detail.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NoteDetailCancelComponent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {
    IconButton(
        modifier = modifier,
        onClick = {
            onClick()
        }
    ) {
        Icon(imageVector = Icons.Default.Close, contentDescription = null)
    }
}