package nl.codingwithlinda.notemark.feature_home.presentation.detail.components.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import nl.codingwithlinda.notemark.feature_home.presentation.detail.components.common.NoteDetailCancelComponent
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailAction

@Composable
fun NoteDetailTopBar(
    isSaving: Boolean = false,
    onAction: (NoteDetailAction) -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NoteDetailCancelComponent(
            onClick = {
                onAction(NoteDetailAction.ConfirmCancelDialog)
            }
        )

        NoteDetailSaveComponent(
            isSaving = isSaving,
            onClick = {
                onAction(NoteDetailAction.SaveAction)
            }
        )


    }
}