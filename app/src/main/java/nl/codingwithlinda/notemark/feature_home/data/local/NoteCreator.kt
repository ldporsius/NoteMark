package nl.codingwithlinda.notemark.feature_home.data.local

import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.core.navigation.dto.EditNoteDto
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.CreateNoteRequestDto
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.NoteResponseDto
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.UpdateNoteRequestDto
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
object NoteCreator {

    fun hexUuidToUuid(hexUuid: String): String{
        return Uuid.parseHex(hexUuid).toString()
    }
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

    fun editNoteDtoToNote(editNoteDto: EditNoteDto): Note {
        return Note(
            id = editNoteDto.id,
            title = editNoteDto.title,
            content = editNoteDto.content,
            dateCreated = editNoteDto.dateCreated,
            dateLastUpdated = editNoteDto.dateCreated
        )
    }
    fun updateNote(note: Note): Note{
        val now = System.currentTimeMillis()
        val dateUpdated = iso8601FromMillis(now)

        return note.copy(
            dateLastUpdated = dateUpdated
        )
    }

    fun createRemoteDto(note: Note): CreateNoteRequestDto {
        return CreateNoteRequestDto(
            id = hexUuidToUuid(note.id),
            title = note.title,
            content = note.content,
            createdAt = note.dateCreated,
            lastEditedAt = note.dateLastUpdated
        )
    }

    fun createRemoteUpdateDto(note: Note): UpdateNoteRequestDto {
        return UpdateNoteRequestDto(
            id = hexUuidToUuid(note.id),
            title = note.title,
            content = note.content,
            createdAt = note.dateCreated,
            lastEditedAt = note.dateLastUpdated
        )
    }
}