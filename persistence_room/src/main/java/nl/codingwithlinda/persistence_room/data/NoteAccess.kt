package nl.codingwithlinda.persistence_room.data

import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.core.domain.persistence.LocalAccess
import nl.codingwithlinda.persistence_room.dao.NoteDao
import nl.codingwithlinda.persistence_room.model.NoteEntity

class NoteAccess(
    private val noteDao: NoteDao,
): LocalAccess<NoteEntity, String> {

    override suspend fun create(entity: NoteEntity) {
            noteDao.insertNote(entity)
    }

    override suspend fun readByKey(filter: String): NoteEntity? {
       return noteDao.getNoteById(filter)
    }

    override suspend fun update(entity: NoteEntity) {
            noteDao.upsertNote(entity)
    }

    override suspend fun delete(id: String) {
         noteDao.deleteNoteById(id)
    }

    override val readAllFlow: Flow<List<NoteEntity>>
        get() = noteDao.getAllNotes()

    override suspend fun deleteAll() {
        noteDao.deleteAllNotes()
    }

}