package nl.codingwithlinda.notemark.feature_home.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.core.data.remote.common.DefaultHttpClient
import nl.codingwithlinda.notemark.core.data.remote.common.HttpRoutes
import nl.codingwithlinda.notemark.core.domain.error.RemoteError
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.CreateNoteRequestDto
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.FetchNotesRequestDto
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.FetchNotesResponseDto
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.NoteResponseDto

class NotesService(
    private val client: HttpClient
) {

    suspend fun createNote(note: Note): Result<NoteResponseDto, RemoteError>{
        try {
            val response = client.post(HttpRoutes.CREATE_NOTE_URL) {
                contentType(ContentType.Application.Json)
                setBody(CreateNoteRequestDto(
                    id = note.id,
                    title = note.title,
                    content = note.content,
                    createdAt = note.dateCreated,
                    lastEditedAt = note.dateLastUpdated
                ))
            }
            val dto = response.body<NoteResponseDto>()
            return Result.Success(dto)
        }catch (e: Exception){
            e.printStackTrace()
            return Result.Error(RemoteError.UnknownError)
        }
    }

    suspend fun updateNote(note: Note): Result<NoteResponseDto, RemoteError>{
        try {
            val response = client.put(HttpRoutes.CREATE_NOTE_URL) {
                contentType(ContentType.Application.Json)
                setBody(CreateNoteRequestDto(
                    id = note.id,
                    title = note.title,
                    content = note.content,
                    createdAt = note.dateCreated,
                    lastEditedAt = note.dateLastUpdated
                ))
            }
            val dto = response.body<NoteResponseDto>()
            return Result.Success(dto)
        }catch (e: Exception){
            e.printStackTrace()
            return Result.Error(RemoteError.UnknownError)
        }
    }

    suspend fun getNotes(fetchNotesRequestDto: FetchNotesRequestDto): Result<FetchNotesResponseDto, RemoteError>{
        try {
            val response = client.get(HttpRoutes.FETCH_NOTES_URL){
                url {
                    parameters.append("page", fetchNotesRequestDto.page.toString())
                    parameters.append("size", fetchNotesRequestDto.size.toString())
                }
            }
            val dto = response.body<FetchNotesResponseDto>()
            return Result.Success(dto)
        }catch (e: Exception){
            e.printStackTrace()
            return Result.Error(RemoteError.UnknownError)
        }
    }
}