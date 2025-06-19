package nl.codingwithlinda.notemark.feature_home.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import nl.codingwithlinda.notemark.core.navigation.dto.EditNoteDto
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.feature_home.presentation.detail.components.NoteDetailScreen
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailUiState

@Preview
@Composable
private fun NoteDetailScreenPreview() {
    val editNoteDto = EditNoteDto(
        id = "1",
        title = "My note",
        content = "This is my note"
    )
    NoteMarkTheme {
        NoteDetailScreen(
            uiState = NoteDetailUiState(
                editNoteDto = editNoteDto
            ),
            onAction = {}
        )

    }
}