package nl.codingwithlinda.notemark.feature_home.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.core.presentation.toUiText
import nl.codingwithlinda.notemark.core.util.Error
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.core.util.SnackBarController
import nl.codingwithlinda.notemark.core.util.SnackbarEvent
import nl.codingwithlinda.notemark.core.util.UiText
import nl.codingwithlinda.notemark.feature_home.data.local.NoteCreator
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailAction
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailUiState
import nl.codingwithlinda.notemark.feature_home.presentation.model.toEditNoteUi
import nl.codingwithlinda.notemark.feature_home.presentation.model.toUi
import nl.codingwithlinda.notemark.feature_home.presentation.model.toUiDetail

class NoteDetailViewModel(
    private val noteRepository: NoteRepository,
    private val noteId: String,
): ViewModel() {

    val navBackChannel = Channel<Unit>()
    private var oldNote: Note? = null
    private var isNewNote = false
    private val _uiState = MutableStateFlow(NoteDetailUiState())
    val uiState = _uiState.asStateFlow()

    private fun sendError(error: Error) = viewModelScope.launch{
        SnackBarController.sendEvent(
            SnackbarEvent(
                message = error.toUiText(),
            )
        )
    }
    private fun sendMessage(msg: String) = viewModelScope.launch{
        SnackBarController.sendEvent(
            SnackbarEvent(
                message = UiText.DynamicText(msg),
            )
        )
    }
    init {

        //save note if not exists
        //not sure why this is needed
        viewModelScope.launch(Dispatchers.IO) {
            val existsRes = noteRepository.getNote(noteId)
            if (existsRes is Result.Success){
                oldNote = existsRes.data
            }
            if (existsRes is Result.Error){
                //note is not found
                println("NOTE DETAIL VIEWMODEL. NOTE NOT FOUND")
                isNewNote = true

                val newNote = NoteCreator.newNote(title = "New note", content = "").copy(
                    id = noteId
                )
                val result = noteRepository.createNote(newNote)
                if (result is Result.Error){
                    sendError(result.error)
                }

                if (result is Result.Success){
                    oldNote = result.data
                }

            }

            oldNote?.let {note ->
                _uiState.update {
                    it.copy(
                        note = note.toUiDetail(),
                        editNoteDto = note.toEditNoteUi()
                    )
                }
            }

        }
    }


    fun deleteNewNoteOnCancel() {
        if (isNewNote) {
            viewModelScope.launch(NonCancellable) {
                val result = noteRepository.deleteNote(noteId)
                if (result is Result.Error) {
                    sendError(result.error)
                }
            }
        }
        navBackChannel.trySend(Unit)
    }


    fun onAction(action: NoteDetailAction) {
        when(action) {
            NoteDetailAction.ConfirmCancelDialog -> {
                //if content is edited show dialog to save or dismiss
                //else navigate back
                if (uiState.value.editNoteDto == null) navBackChannel.trySend(Unit)
                val isTitleChanged = oldNote?.title != uiState.value.editNoteDto?.title
                val isContentChanged = oldNote?.content != uiState.value.editNoteDto?.content
                if (isTitleChanged || isContentChanged) {
                    //show dialog
                    _uiState.update {
                        it.copy(
                            showConfirmCancelDialog = true
                        )
                    }
                }else{
                   deleteNewNoteOnCancel()
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
                println("NOTE DETAIL VIEW MODEL CANCEL ACTION IS CALLED. IS NEW NOTE: $isNewNote")
                deleteNewNoteOnCancel()
            }
            NoteDetailAction.SaveAction -> {
                //save note
                _uiState.update {
                    it.copy(
                        isSaving = true
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    uiState.value.editNoteDto?.let { editnote ->
                        val note = NoteCreator.editNoteDtoToNote(editnote)
                        val update = NoteCreator.updateNote(note)
                        val result = noteRepository.updateNote(
                            update
                        )
                        _uiState.update {
                            it.copy(
                                isSaving = false,
                                note = update.toUiDetail()
                            )
                        }

                        when(result) {
                            is Result.Success -> {
                                sendMessage("Note saved successfully")
                                navBackChannel.send(Unit)
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

    override fun onCleared() {
        super.onCleared()
        println("NOTE DETAIL VIEW MODEL CLEARED")
    }
}