package nl.codingwithlinda.notemark.feature_home.presentation.list.state

import nl.codingwithlinda.notemark.feature_home.presentation.model.NoteUi

data class NoteListUiState(
    val notes: List<NoteUi> = emptyList(),
)
