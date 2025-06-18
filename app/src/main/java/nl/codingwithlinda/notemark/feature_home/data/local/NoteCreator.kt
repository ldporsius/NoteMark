package nl.codingwithlinda.notemark.feature_home.data.local

import nl.codingwithlinda.core.domain.model.Note
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
object NoteCreator {

    fun iso8601ToInstant(iso8601: String): org.threeten.bp.Instant {
        return org.threeten.bp.Instant.parse(iso8601)
    }
    fun iso8601FromMillis(now: Long): String {
        return Instant.fromEpochMilliseconds(now).toString()
    }
    fun newNote(title: String, content: String): Note{
        val now = System.currentTimeMillis()
        val dateCreated = iso8601FromMillis(now)
        val id = Uuid.random().toHexString()

        return Note(
            id = id,
            title = title,
            content = content,
            dateCreated = dateCreated,
            dateLastUpdated = dateCreated
        )
    }

    fun updateNote(note: Note): Note{
        val now = System.currentTimeMillis()
        val dateUpdated = iso8601FromMillis(now)

        return note.copy(
            dateLastUpdated = dateUpdated
        )
    }
}