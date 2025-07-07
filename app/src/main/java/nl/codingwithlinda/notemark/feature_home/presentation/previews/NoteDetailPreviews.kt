package nl.codingwithlinda.notemark.feature_home.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import nl.codingwithlinda.notemark.core.navigation.dto.EditNoteDto
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.feature_home.presentation.detail.components.edit.NoteDetailScreen
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailUiState

@Preview
@Composable
private fun NoteDetailScreenPreview() {
    val editNoteDto = EditNoteDto(
        id = "1",
        title = "My note",
        content = "This is my note",
        dateCreated = "2023-01-01T00:00:00.000Z"
    )
    NoteMarkTheme {
        NoteDetailScreen(
            uiState = NoteDetailUiState(
                editNoteDto = editNoteDto,
                isSaving = true
            ),
            onAction = {}
        )

    }
}