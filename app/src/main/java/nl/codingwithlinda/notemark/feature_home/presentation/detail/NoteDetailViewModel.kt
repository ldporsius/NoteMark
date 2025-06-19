package nl.codingwithlinda.notemark.feature_home.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.navigation.dto.EditNoteDto
import nl.codingwithlinda.notemark.core.presentation.toUiText
import nl.codingwithlinda.notemark.core.util.Error
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.core.util.SnackBarController
import nl.codingwithlinda.notemark.core.util.SnackbarEvent
import nl.codingwithlinda.notemark.feature_home.data.local.NoteCreator
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailAction
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailUiState

class NoteDetailViewModel(
    private val noteRepository: NoteRepository,
    private val noteDto: EditNoteDto,
    private val navBack: () -> Unit
): ViewModel() {

    private val _uiState = MutableStateFlow(NoteDetailUiState())
    val uiState = _uiState.asStateFlow()

    private fun sendError(error: Error) = viewModelScope.launch{
        SnackBarController.sendEvent(
            SnackbarEvent(
                message = error.toUiText(),
            )
        )
    }
    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                 editNoteDto = noteDto
                )
            }
        }
    }

    fun onAction(action: NoteDetailAction) {
        when(action) {
            NoteDetailAction.ConfirmCancelDialog -> {
                //if content is edited show dialog to save or dismiss
                //else navigate back
                if (uiState.value.editNoteDto == null) navBack()
                val isTitleChanged = noteDto.title != uiState.value.editNoteDto?.title
                val isContentChanged = noteDto.content != uiState.value.editNoteDto?.content
                if (isTitleChanged || isContentChanged) {
                    //show dialog
                    _uiState.update {
                        it.copy(
                            showConfirmCancelDialog = true
                        )
                    }
                }else{
                    navBack()
                }
            }
            NoteDetailAction.DismissCancelDialog -> {
                _uiState.update {
                    it.copy(
                        showConfirmCancelDialog = false
                    )
                }
            }
            NoteDetailAction.CancelAction -> {
               navBack()
            }
            NoteDetailAction.SaveAction -> {
                //save note
                viewModelScope.launch {
                    uiState.value.editNoteDto?.let { editnote ->
                        val note = NoteCreator.editNoteDtoToNote(editnote)
                        val update = NoteCreator.updateNote(note)
                        val result = noteRepository.createNote(
                            update
                        )
                        when(result) {
                            is Result.Success -> {
                                navBack()
                            }
                            is Result.Error -> {
                                sendError(result.error)
                            }
                        }
                    }
                }
            }

            is NoteDetailAction.ContentChanged -> {
               _uiState.update {
                   it.copy(
                       editNoteDto = it.editNoteDto?.copy(
                           content = action.content
                       )
                   )
               }
            }
            is NoteDetailAction.TitleChanged -> {
                _uiState.update {
                    it.copy(
                        editNoteDto = it.editNoteDto?.copy(
                            title = action.title
                        )
                    )
                }
            }
        }

    }
}