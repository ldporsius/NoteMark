package nl.codingwithlinda.notemark.feature_home.presentation.list.state

sealed interface NoteListAction {
    data object CreateNoteAction : NoteListAction
    data class EditNoteAction(val noteId: String) : NoteListAction
    data object DismissDeleteConfirmationDialog : NoteListAction
    data class ShowDeleteConfirmationDialog(val noteId: String) : NoteListAction
    data object DeleteNoteAction : NoteListAction
}