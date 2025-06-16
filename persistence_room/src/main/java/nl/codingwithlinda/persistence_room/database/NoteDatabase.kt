package nl.codingwithlinda.persistence_room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import nl.codingwithlinda.persistence_room.dao.NoteDao
import nl.codingwithlinda.persistence_room.model.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao
}