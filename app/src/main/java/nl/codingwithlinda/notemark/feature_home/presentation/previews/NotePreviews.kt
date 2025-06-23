package nl.codingwithlinda.notemark.feature_home.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.feature_home.presentation.list.components.NoteListScreen
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListUiState
import nl.codingwithlinda.notemark.tests.dummy_content.dummyUiNotes

@PreviewScreenSizes
@Composable
private fun NoteListScreenPreview() {
    NoteMarkTheme {
        NoteListScreen(
            uiState = NoteListUiState(
                notes = dummyUiNotes
            ),
            onAction = {},
            onEditNote = {}
        )
    }
}