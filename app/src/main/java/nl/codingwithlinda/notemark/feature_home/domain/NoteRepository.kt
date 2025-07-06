package nl.codingwithlinda.notemark.feature_home.domain

import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.core.domain.error.DataError
import nl.codingwithlinda.notemark.core.util.Result as Result1

interface NoteRepository {

    val notesLocal: Flow<List<Note>>
    suspend fun getNotes(page: Int, size: Int): Result1<List<Note>, DataError>

    suspend fun getNote(noteId: String): Result1<Note, DataError>

    suspend fun createNote(note: Note): Result1<Note, DataError>

    suspend fun updateNote(note: Note): Result1<Note, DataError>

    suspend fun deleteNote(noteId: String): Result1<Unit, DataError>

    suspend fun deleteAllNotes(): Result1<Unit, DataError>

}