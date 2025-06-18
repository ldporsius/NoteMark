package nl.codingwithlinda.notemark.feature_home.data.local

import kotlinx.coroutines.flow.firstOrNull
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.core.domain.error.DataError
import nl.codingwithlinda.notemark.core.domain.error.LocalError
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.model.EditNoteUi
import nl.codingwithlinda.notemark.feature_home.presentation.model.NoteUi
import nl.codingwithlinda.persistence_room.public_access.LocalNoteAccess

val longDummyText = "The quiet hum of the morning air was broken only by the soft rustle of leaves dancing in the breeze. A cup of coffee steamed gently in hand, warmth seeping into chilled fingers. Thoughts flowed freely, unbothered by structure or form—just fragments of memory and hope stitched together. In this stillness, inspiration felt close, like a familiar friend waiting to be acknowledged and welcomed in. Pages remained blank, yet full of potential. Each pause between thoughts was its own kind of music. The world outside hadn’t changed, but the lens through which it was viewed felt freshly cleaned—clearer, softer. Clouds moved lazily across the sky, painting slow-moving stories above. A breeze swept in, carrying with it the faint scent of earth and something that reminded one of home. The clinking of a spoon against a ceramic mug, the distant bark of a dog, a laugh from a neighbor’s open window—each moment ordinary, yet profound. And somehow, all of it seemed to whisper: write, because this matters."
val tempNotes = List(10){
    NoteCreator.newNote(title = "Note $it", content = "This is note $it")
}
val olderNotes = tempNotes.map {
    it.copy(
        dateCreated = "2022-01-01T00:00:00.000Z",
        content = longDummyText
    )
}

val dummyUiNotes = List(10){
    NoteUi(
        id = it.toString(),
        date = "14 jul",
        title = "My first note",
        content = longDummyText
    )
}
val dummyNoteNoWhiteSpace = NoteCreator.newNote(
    title = "No white space",
    content = longDummyText.filterNot{it.isWhitespace()}
)

val dummyEditNote = EditNoteUi(
    id = dummyNoteNoWhiteSpace.id,
    title = dummyNoteNoWhiteSpace.title,
    content = dummyNoteNoWhiteSpace.content
)

class NoteRepositoryImpl(
    private val noteAccess: LocalNoteAccess
): NoteRepository {

    override suspend fun getNotes(page: Int, size: Int): Result<List<Note>, DataError> {

        val notes = noteAccess.readAllFlow.firstOrNull()?.map {
            it
        }
        return if (notes == null) {
            Result.Success(emptyList())
        }
        else {
            Result.Success(notes)
        }
    }

    override suspend fun getNote(noteId: String): Result<Note, DataError> {
        val note = noteAccess.readByKey(noteId)

        return if (note == null) {
            Result.Error(DataError.LocalDataError(LocalError.NOT_FOUND))
        }
        else {
            Result.Success(note)
        }
    }

    override suspend fun createNote(note: Note): Result<Note, DataError> {
        //save to db and to remote storage
        try {
            noteAccess.create(note)
            return Result.Success(note)

        }catch (e: Exception){
            return Result.Error(DataError.LocalDataError(LocalError.DISK_FULL))
        }
    }

    override suspend fun updateNote(note: Note): Result<Note, DataError> {
        //save to db and to remote storage
        try {
            noteAccess.update(note)
            return Result.Success(note)
        }catch (e: Exception){
            return Result.Error(DataError.LocalDataError(LocalError.DISK_FULL))
        }
    }

    override suspend fun deleteNote(noteId: String): Result<Unit, DataError> {
        return Result.Success(Unit)
    }

}