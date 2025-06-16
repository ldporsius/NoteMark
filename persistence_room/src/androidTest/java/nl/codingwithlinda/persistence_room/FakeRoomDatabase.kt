package nl.codingwithlinda.persistence_room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import nl.codingwithlinda.persistence_room.data.NoteAccess
import nl.codingwithlinda.persistence_room.database.NoteDatabase

class FakeRoomDatabase {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java).build()

    val dao = db.noteDao
    val scope = CoroutineScope(Dispatchers.Default)

    val noteAccess = NoteAccess(
        noteDao = dao,
        scope = scope
    )

}
