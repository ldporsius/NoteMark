package nl.codingwithlinda.persistence_room.public_access

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.core.domain.persistence.LocalAccess
import nl.codingwithlinda.persistence_room.data.NoteAccess
import nl.codingwithlinda.persistence_room.data.toDomain
import nl.codingwithlinda.persistence_room.data.toRoom
import nl.codingwithlinda.persistence_room.database.NoteDatabase

class LocalNoteAccess(
    noteDatabase: NoteDatabase,
    applicationScope: CoroutineScope
): LocalAccess<Note, String> {


    private val noteAccess = NoteAccess(
        noteDao = noteDatabase.noteDao,
        scope = applicationScope
    )

    override suspend fun create(entity: Note) {
        noteAccess.create(entity.toRoom())
    }

    override suspend fun readByKey(filter: String): Note? {
        return noteAccess.readByKey(filter)?.toDomain()
    }

    override suspend fun update(entity: Note) {
        noteAccess.update(entity.toRoom())
    }

    override suspend fun delete(entity: Note) {
        noteAccess.delete(entity.toRoom())
    }

    override val readAllFlow: Flow<List<Note>>
        get() = noteAccess.readAllFlow.map {
            it.map { noteEntity ->
                noteEntity.toDomain()
            }
        }

}