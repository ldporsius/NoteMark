package nl.codingwithlinda.notemark.feature_home.presentation.detail.state

sealed interface NoteDetailAction {
    data object CancelAction: NoteDetailAction
    data object SaveAction: NoteDetailAction
    data class TitleChanged(val title: String): NoteDetailAction
    data class ContentChanged(val content: String): NoteDetailAction

}