package nl.codingwithlinda.notemark.feature_home.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.core.presentation.toUiText
import nl.codingwithlinda.notemark.core.util.Error
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.design_system.util.SnackBarController
import nl.codingwithlinda.notemark.design_system.util.SnackbarEvent
import nl.codingwithlinda.notemark.feature_home.data.local.NoteCreator
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListAction
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListUiState
import nl.codingwithlinda.notemark.core.navigation.dto.EditNoteDto
import nl.codingwithlinda.notemark.feature_home.presentation.model.NoteUi
import nl.codingwithlinda.notemark.feature_home.presentation.model.toEditNoteUi
import nl.codingwithlinda.notemark.feature_home.presentation.model.toUi


class NoteListViewModel(
    private val noteRepository: NoteRepository,
    private val onEditNote: (EditNoteDto) -> Unit,
): ViewModel() {

    private val NEW_NOTE_TITLE = "New note"
    private val notes = noteRepository.notesLocal
    private val _uiState = MutableStateFlow(NoteListUiState())
    val uiState = combine(_uiState, notes) { uiState, notes ->
        uiState.copy(notes = notes.map { parseNote(it) })
    }.stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000), _uiState.value)

    init {
        viewModelScope.launch {
            val notesRes = noteRepository.getNotes(-1, 0)
            when(notesRes){
                is nl.codingwithlinda.notemark.core.util.Result.Success -> {
                    val notes = notesRes.data.map { parseNote(it) }
                    _uiState.update {
                        it.copy(notes = notes)
                    }
                }
                is Result.Error-> {
                    //handle error
                }
            }
        }
    }

    private fun parseNote(note: Note): NoteUi {
        val today = org.threeten.bp.ZonedDateTime.now()
        return note.toUi(today)
    }

    private fun sendError(error: Error)= viewModelScope.launch{
        SnackBarController.sendEvent(
            SnackbarEvent(
                message = error.toUiText()
            )
        )
    }
    fun onAction(action: NoteListAction){
        when(action){
            NoteListAction.CreateNoteAction -> {
                viewModelScope.launch {
                    val note = NoteCreator.newNote(title = NEW_NOTE_TITLE, content = "")
                    val editNoteUi = note.toEditNoteUi()
                    println("NOTE LIST VIEWMODEL CALLS ON EDIT NOTE WITH EDIT NOTE UI $editNoteUi")
                    onEditNote(editNoteUi)
                }
            }
            is NoteListAction.ShowDeleteConfirmationDialog -> {
                _uiState.update {
                    it.copy(showDeleteConfirmationDialog = true,
                        toDeleteNoteId = action.noteId
                    )
                }
            }
            NoteListAction.DismissDeleteConfirmationDialog -> {
                _uiState.update {
                    it.copy(showDeleteConfirmationDialog = false,
                        toDeleteNoteId = null
                    )
                }
            }
            is NoteListAction.DeleteNoteAction -> {
                viewModelScope.launch {
                    uiState.value.toDeleteNoteId?.let {
                        try {
                            val res =  noteRepository.deleteNote(it)
                            when(res){
                                is Result.Error-> {
                                    sendError(res.error)
                                }
                                is Result.Success -> Unit
                            }
                            _uiState.update {
                                it.copy(
                                    showDeleteConfirmationDialog = false,
                                    toDeleteNoteId = null
                                )
                            }
                        }catch (e: Exception){
                            e.printStackTrace()
                        }
                    }
                }
            }

            is NoteListAction.EditNoteAction -> {
                viewModelScope.launch {
                    val note = noteRepository.getNote(action.noteId)
                    when(note){
                        is Result.Error -> {
                            sendError(note.error)
                        }
                        is Result.Success-> {
                            val editNoteUi = note.data.toEditNoteUi()
                            onEditNote(editNoteUi)
                        }
                    }
                }
            }
        }
    }
}