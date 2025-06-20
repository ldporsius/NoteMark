package nl.codingwithlinda.notemark.feature_home.presentation.detail.state

import nl.codingwithlinda.notemark.core.navigation.dto.EditNoteDto

data class NoteDetailUiState(
    val editNoteDto: EditNoteDto? = null,
    val showConfirmCancelDialog: Boolean = false,
    val isSaving: Boolean = false
)
