package nl.codingwithlinda.notemark.feature_home.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailAction
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailUiState
import nl.codingwithlinda.notemark.feature_home.presentation.model.toEditNoteUi

class NoteDetailViewModel(
    private val noteRepository: NoteRepository,
    private val noteId: String
): ViewModel() {

    private val _uiState = MutableStateFlow(NoteDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val noteResult = noteRepository.getNote(noteId)
            when(noteResult) {
                is Result.Error -> {
                    //handle error
                }
                is Result.Success-> {
                    val note = noteResult.data
                    _uiState.update {
                        it.copy(
                            editNoteUi = note.toEditNoteUi()
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: NoteDetailAction) {
        when(action) {
            NoteDetailAction.CancelAction -> {
                //if content is edited show dialog to save or dismiss
                //else navigate back
            }
            NoteDetailAction.SaveAction -> {
                //save note
            }

            is NoteDetailAction.ContentChanged -> {
                //update content
            }
            is NoteDetailAction.TitleChanged -> {
                //update title
            }
        }

    }
}