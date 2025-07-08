package nl.codingwithlinda.notemark.feature_home.presentation.detail.state

import nl.codingwithlinda.notemark.core.navigation.dto.EditNoteDto
import nl.codingwithlinda.notemark.feature_home.presentation.model.NoteUi

data class NoteDetailUiState(
    val note: NoteUi? = null,
    val editNoteDto: EditNoteDto? = null,
    val showConfirmCancelDialog: Boolean = false,
    val isSaving: Boolean = false
)
