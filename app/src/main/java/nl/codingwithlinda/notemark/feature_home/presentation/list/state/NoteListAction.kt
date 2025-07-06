package nl.codingwithlinda.notemark.feature_home.presentation.list.state

import androidx.compose.ui.geometry.Offset

sealed interface NoteListAction {
    data object DismissDeleteConfirmationDialog : NoteListAction
    data class ShowDeleteConfirmationDialog(val noteId: String) : NoteListAction
    data object DeleteNoteAction : NoteListAction
}