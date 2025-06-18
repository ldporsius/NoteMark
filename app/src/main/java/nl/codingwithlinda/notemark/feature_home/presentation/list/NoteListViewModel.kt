package nl.codingwithlinda.notemark.feature_home.presentation.list

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListUiState
import nl.codingwithlinda.notemark.feature_home.presentation.model.toUi
import java.time.ZonedDateTime
import java.util.Date


class NoteListViewModel(
    private val noteRepository: NoteRepository
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
}