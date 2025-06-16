package nl.codingwithlinda.persistence_room

import kotlinx.coroutines.runBlocking
import org.junit.Test

class NoteAccessTest {

    private val noteAccess  = FakeRoomDatabase().noteAccess

    @Test
    fun testNoteAccess() {
        runBlocking {
            val result = noteAccess.readByKey("test")

            println(result)
        }
    }
}