package nl.codingwithlinda.notemark.feature_home.data.local

import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.core.domain.error.DataError
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository

class NoteRepositoryImpl: NoteRepository {
    val tempNotes = List(10){
        NoteCreator.newNote(title = "Note $it", content = "This is note $it")
    }
    override suspend fun getNotes(page: Int, size: Int): Result<List<Note>, DataError> {
        return Result.Success(tempNotes)
    }

    override suspend fun createNote(note: Note): Result<Note, DataError> {
        //save to db and to remote storage
        return Result.Success(note)

    }

    override suspend fun updateNote(note: Note): Result<Note, DataError> {
        //save to db and to remote storage
        return Result.Success(note)
    }
}