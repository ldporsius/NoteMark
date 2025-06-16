package nl.codingwithlinda.persistence_room.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import nl.codingwithlinda.core.domain.persistence.LocalAccess
import nl.codingwithlinda.persistence_room.dao.NoteDao
import nl.codingwithlinda.persistence_room.model.NoteEntity

class NoteAccess(
    private val noteDao: NoteDao,
    private val scope: CoroutineScope
): LocalAccess<NoteEntity, String> {

    override suspend fun create(entity: NoteEntity) {
        scope.launch {
            noteDao.insertNote(entity)
        }
    }

    override suspend fun readByKey(filter: String): NoteEntity? {
       return noteDao.getNoteById(filter)
    }

    override suspend fun update(entity: NoteEntity) {
        scope.launch {
            noteDao.upsertNote(entity)
        }
    }

    override suspend fun delete(entity: NoteEntity) {
        scope.launch {
            noteDao.deleteNoteById(entity.id)
        }
    }

    override val readAllFlow: Flow<List<NoteEntity>>
        get() = noteDao.getAllNotes()


}