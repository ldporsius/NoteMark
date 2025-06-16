package nl.codingwithlinda.persistence_room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import nl.codingwithlinda.persistence_room.database.NoteDatabase

class FakeRoomDatabase(
    private val context: Context = ApplicationProvider.getApplicationContext()
) {
    val db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java).build()
}
