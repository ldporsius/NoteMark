package nl.codingwithlinda.notemark.feature_home.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_home.data.local.NoteCreator
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListAction
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListUiState
import nl.codingwithlinda.notemark.feature_home.presentation.model.toUi


class NoteListViewModel(
    private val noteRepository: NoteRepository,
    private val onEditNote: (String) -> Unit,
): ViewModel() {

    private val _uiState = MutableStateFlow(NoteListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val notesRes = noteRepository.getNotes(-1, 0)
            when(notesRes){
                is nl.codingwithlinda.notemark.core.util.Result.Success -> {
                    val today = org.threeten.bp.ZonedDateTime.now()
                    val notes = notesRes.data.map { it.toUi(today) }
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

    fun onAction(action: NoteListAction){
        when(action){
            NoteListAction.CreateNoteAction -> {
                val note = NoteCreator.newNote(title = "", content = "")
                onEditNote(note.id)
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
                               is Result.Error<*, *> -> {
                                   //handle error
                               }
                               is Result.Success<*, *> -> Unit
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
                onEditNote(action.noteId)
            }

        }
    }
}