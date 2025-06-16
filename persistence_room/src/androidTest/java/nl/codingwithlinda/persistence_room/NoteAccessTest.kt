package nl.codingwithlinda.persistence_room

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.persistence_room.database.NoteDatabase
import nl.codingwithlinda.persistence_room.public_access.LocalNoteAccess
import org.junit.Test

class NoteAccessTest {

    private val db: NoteDatabase  = FakeRoomDatabase().db
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val localNoteAccess = LocalNoteAccess(
        noteDatabase = db,
        applicationScope = scope
    )

    @Test
    fun testNoteAccess() {
        runBlocking {
            val result = localNoteAccess.readByKey("test")

            println(result)
        }
    }
}