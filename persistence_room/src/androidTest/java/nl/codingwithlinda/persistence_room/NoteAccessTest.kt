package nl.codingwithlinda.persistence_room

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.persistence_room.database.NoteDatabase
import nl.codingwithlinda.persistence_room.public_access.LocalNoteAccess
import org.junit.After
import org.junit.Before
import org.junit.Test

class NoteAccessTest {

    private val context: Application = ApplicationProvider.getApplicationContext<Application>()
    private val db: NoteDatabase  = FakeRoomDatabase(context).db
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val localNoteAccess = LocalNoteAccess(
        noteDatabase = db,
    )
    private val note = nl.codingwithlinda.core.domain.model.Note(
        id = "test",
        title = "test",
        content = "test",
        dateCreated = "test"
    )

    @Before
    fun setup(){

    }

    @After
    fun teardown(){

    }

    @Test
    fun testNoteAccess() = runBlocking{
        localNoteAccess.create(
            note
        )

        val result = localNoteAccess.readByKey("test")

        assert(result != null)
        assert(result?.id == "test")
        assert(result?.title == "test")
        assert(result?.content == "test")
        assert(result?.dateCreated == "test")

        localNoteAccess.update(
            note.copy(
                title = "test2"
            )
        )
        val result2 = localNoteAccess.readByKey("test")
        assert(result2?.title == "test2")

        localNoteAccess.delete(note)

        val result3 = localNoteAccess.readByKey("test")
        assert(result3 == null)



    }
}