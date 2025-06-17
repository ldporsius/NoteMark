package nl.codingwithlinda.notemark.feature_home.data.local

import nl.codingwithlinda.core.domain.model.Note
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
object NoteCreator {

    fun iso8601(now: Long): String {
        return Instant.fromEpochMilliseconds(now).toString()
    }
    fun newNote(title: String, content: String): Note{
        val now = System.currentTimeMillis()
        val dateCreated = iso8601(now)
        val id = Uuid.random().toHexString()

        return Note(
            id = id,
            title = title,
            content = content,
            dateCreated = dateCreated,
            dateLastUpdated = dateCreated
        )
    }
}