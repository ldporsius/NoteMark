package nl.codingwithlinda.notemark.feature_home.data.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.core.domain.error.DataError
import nl.codingwithlinda.notemark.core.domain.error.LocalError
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_home.data.remote.NotesService
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.FetchNotesRequestDto
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.mapping.toDomain
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.persistence_room.public_access.LocalNoteAccess
import kotlin.coroutines.coroutineContext


class NoteRepositoryImpl(
    private val applicationScope: CoroutineScope,
    private val noteAccess: LocalNoteAccess,
    private val remoteNoteAccess: NotesService
): NoteRepository {

    override val notesLocal: Flow<List<Note>> = noteAccess.readAllFlow

    override suspend fun getNotes(page: Int, size: Int): Result<List<Note>, DataError> {

        //get remote notes first
        val requestDto = FetchNotesRequestDto(page, size)
        val remoteResult = remoteNoteAccess.getNotes(requestDto)
        when(remoteResult){
            is Result.Error -> return Result.Error(DataError.RemoteDataError(remoteResult.error))
            is Result.Success -> {
                //save to db
                remoteResult.data.toDomain().onEach {
                    noteAccess.create(
                        it.copy(
                            id = NoteCreator.hexUuidToUuid(it.id)
                        )
                    )
                }
            }
        }

        val notes = noteAccess.readAllFlow.firstOrNull()
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
        }catch (e: Exception){
            coroutineContext.ensureActive()
            return Result.Error(DataError.LocalDataError(LocalError.DISK_FULL))
        }

        val remoteDtoResult = applicationScope.async{
            val remoteResult = remoteNoteAccess.createNote(note)
            return@async remoteResult
        }.await()
        if (remoteDtoResult is Result.Error){
            return Result.Error(remoteDtoResult.error)
        }
        return Result.Success(note)
    }

    override suspend fun updateNote(note: Note): Result<Note, DataError> {
        //save to db and to remote storage
        try {
            noteAccess.update(note)
        }catch (e: Exception){
            return Result.Error(DataError.LocalDataError(LocalError.UnknownError))
        }

        applicationScope.launch {
            val remoteResult = remoteNoteAccess.updateNote(note)
            if (remoteResult is Result.Error){
                println("NOTE REPOSITORY UPDATE NOTE ERROR: ${remoteResult.error}")
            }
        }.join()

        return Result.Success(note)
    }

    override suspend fun deleteNote(noteId: String): Result<Unit, DataError> {
        //delete from db and remote storage
        val remoteId = NoteCreator.hexUuidToUuid(noteId)
        try {
            noteAccess.delete(noteId)
            noteAccess.delete(remoteId)
        }catch (e: Exception){
            return Result.Error(DataError.LocalDataError(LocalError.UnknownError))
        }

        val remoteDtoResult = applicationScope.async{
            val remoteResult = remoteNoteAccess.deleteNote(remoteId)
            return@async remoteResult
        }.await()
        if (remoteDtoResult is Result.Error){
            return Result.Error(remoteDtoResult.error)
        }
        return Result.Success(Unit)
    }

    override suspend fun deleteAllNotes(): Result<Unit, DataError> {
        try {
            noteAccess.deleteAll()
            return Result.Success(Unit)
        }catch (e: Exception){
            return Result.Error(DataError.LocalDataError(LocalError.UnknownError))
        }
    }

}