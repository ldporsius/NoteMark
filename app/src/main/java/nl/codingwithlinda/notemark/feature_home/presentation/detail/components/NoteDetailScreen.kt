package nl.codingwithlinda.notemark.feature_home.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailAction
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailUiState

@Composable
fun NoteDetailScreen(
    uiState: NoteDetailUiState,
    onAction: (NoteDetailAction) -> Unit,
) {

    uiState.editNoteUi ?: return
    Scaffold(
        topBar = {
            NoteDetailTopBar(
                onAction = onAction
            )
        }
    ) {
        padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            TextField(
                value =uiState.editNoteUi.title,
                onValueChange = {
                    onAction(NoteDetailAction.TitleChanged(it))
                }
            )
            TextField(
                value = uiState.editNoteUi.content,
                onValueChange = {
                    onAction(NoteDetailAction.ContentChanged(it))
                }
            )
        }

    }
}