package nl.codingwithlinda.persistence_room.database

import androidx.room.RoomDatabase
import nl.codingwithlinda.persistence_room.dao.NoteDao

abstract class NoteDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao
}