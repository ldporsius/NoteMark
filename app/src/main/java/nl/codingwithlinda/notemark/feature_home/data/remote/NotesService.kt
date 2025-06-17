package nl.codingwithlinda.notemark.feature_home.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.core.data.auth.common.DefaultHttpClient
import nl.codingwithlinda.notemark.core.data.auth.common.HttpRoutes
import nl.codingwithlinda.notemark.core.domain.error.RemoteError
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.CreateNoteRequestDto
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.CreateNoteResponseDto
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class NotesService(
    private val client: HttpClient = DefaultHttpClient.httpClient
) {


    @OptIn(ExperimentalTime::class)
    suspend fun createNote(note: Note): Result<CreateNoteResponseDto, RemoteError>{
        try {
            val lastEditedTimeStamp = System.currentTimeMillis()
            val instant = Instant.fromEpochMilliseconds(lastEditedTimeStamp)
            val iso8601 = instant.toString()
            val response = client.post(HttpRoutes.CREATE_NOTE_URL) {
                contentType(ContentType.Application.Json)
                setBody(CreateNoteRequestDto(
                    id = note.id,
                    title = note.title,
                    content = note.content,
                    createdAt = note.dateCreated,
                    lastEditedAt = iso8601
                ))
            }
            val dto = response.body<CreateNoteResponseDto>()
            return Result.Success(dto)
        }catch (e: Exception){
            return Result.Error(RemoteError.UnknownError)
        }
    }
}