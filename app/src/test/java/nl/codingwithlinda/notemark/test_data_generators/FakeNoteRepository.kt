package nl.codingwithlinda.notemark.test_data_generators

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.core.domain.error.DataError
import nl.codingwithlinda.notemark.core.domain.error.LocalError
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository

class FakeNoteRepository: NoteRepository {

    private val notesState = MutableStateFlow<List<Note>>(emptyList())

    override val notesLocal: Flow<List<Note>>
        get() = notesState

    override suspend fun getNotes(page: Int, size: Int): Result<List<Note>, DataError> {
        return Result.Success(notesState.value)
    }

    override suspend fun getNote(noteId: String): Result<Note, DataError> {
        if (notesState.value.isEmpty()) return Result.Error(DataError.LocalDataError(LocalError.NOT_FOUND))
        val note = notesState.value.firstOrNull { it.id == noteId } ?: return Result.Error(DataError.LocalDataError(LocalError.NOT_FOUND))
        return Result.Success(note)
    }

    override suspend fun createNote(note: Note): Result<Note, DataError> {
        notesState.value += note
        return Result.Success(note)
    }

    override suspend fun updateNote(note: Note): Result<Note, DataError> {
        notesState.value = notesState.value.map { if (it.id == note.id) note else it }
        return Result.Success(note)
    }

    override suspend fun deleteNote(noteId: String): Result<Unit, DataError> {
        notesState.value = notesState.value.filter { it.id != noteId }
        return Result.Success(Unit)
    }
}