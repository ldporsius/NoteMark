package nl.codingwithlinda.notemark.feature_home.presentation.detail.components.view.state

import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailViewMode

sealed interface NoteViewStateAction {
    data object TapAnywhere: NoteViewStateAction
    data object Scroll: NoteViewStateAction
    data class ChangeMode(val mode: NoteDetailViewMode): NoteViewStateAction
}