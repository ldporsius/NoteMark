package nl.codingwithlinda.notemark.feature_home.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.core.data.remote.common.HttpRoutes
import nl.codingwithlinda.notemark.core.domain.error.DataError
import nl.codingwithlinda.notemark.core.domain.error.RemoteError
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_home.data.local.NoteCreator
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.FetchNotesRequestDto
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.FetchNotesResponseDto
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.NoteResponseDto
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.UpdateNoteResponseDto

class NotesService(
    private val client: HttpClient
) {

    suspend fun createNote(note: Note): Result<NoteResponseDto, DataError.RemoteDataError>{
        try {

            val response = client.post(HttpRoutes.CREATE_NOTE_URL) {
                contentType(ContentType.Application.Json)
                setBody(NoteCreator.createRemoteDto(note))
            }

            val dto = response.body<NoteResponseDto>()
            return Result.Success(dto)
        }
        catch (e: ResponseException){
            val code = e.response.status.value
            val message = e.response.status.description
            val bodyTxt = e.response.bodyAsText()
            println("NOTES SERVICE CREATE NOTE ERROR:$code $message, $bodyTxt")
            val error = RemoteError.entries.find {
                it.code == code
            }
            error?.run {
                return Result.Error(DataError.RemoteDataError(this, bodyTxt))
            }
            return Result.Error(DataError.RemoteDataError(RemoteError.UnknownError, e.message))
        }
        catch (e: NoTransformationFoundException){
            e.printStackTrace()
            return Result.Error(DataError.RemoteDataError(RemoteError.UnknownError, e.message))
        }
        catch (e: Exception){
            e.printStackTrace()
            return Result.Error(DataError.RemoteDataError(RemoteError.UnknownError, e.message))
        }
    }

    suspend fun updateNote(note: Note): Result<UpdateNoteResponseDto, DataError.RemoteDataError>{
        println("NOTES SERVICE UPDATE NOTE: $note")
        try {
            val response = client.put(HttpRoutes.CREATE_NOTE_URL) {
                expectSuccess = true
                contentType(ContentType.Application.Json)
                setBody(NoteCreator.createRemoteUpdateDto(note))
            }
            val dto = response.body<UpdateNoteResponseDto>()
            return Result.Success(dto)
        } catch (e: ResponseException){
            val code = e.response.status.value
            val message = e.response.status.description
            val bodyTxt = e.response.bodyAsText()
            println("NOTES SERVICE UPDATE NOTE Response ERROR:$code $message, $bodyTxt")
            val error = RemoteError.entries.find {
                it.code == code
            }
            error?.run {
                return Result.Error(DataError.RemoteDataError(this, bodyTxt))
            }
            return Result.Error(DataError.RemoteDataError(RemoteError.UnknownError, e.message))
        }
        catch (e: NoTransformationFoundException){
            println("NOTES SERVICE UPDATE NOTE NoTransformationFoundException EXCEPTION: ${e.message}")
            e.printStackTrace()
            return Result.Error(DataError.RemoteDataError(RemoteError.UnknownError, e.message))
        }
        catch (e: Exception){
            println("NOTES SERVICE UPDATE NOTE OTHER EXCEPTION: ${e.message}")
            e.printStackTrace()
            return Result.Error(DataError.RemoteDataError(RemoteError.UnknownError, e.message))
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

    suspend fun deleteNote(id: String): Result<Unit, DataError.RemoteDataError>{
        try {
            val response = client.delete(HttpRoutes.DELETE_NOTE_URL + "/$id")
            return Result.Success(Unit)
        } catch (e: ResponseException){
            val code = e.response.status.value
            val message = e.response.status.description
            val bodyTxt = e.response.bodyAsText()
            println("NOTES SERVICE DELETE NOTE ERROR:$code $message, $bodyTxt")
            val error = RemoteError.entries.find {
                it.code == code
            }
            error?.run {
                return Result.Error(DataError.RemoteDataError(this, bodyTxt))
            }
            return Result.Error(DataError.RemoteDataError(RemoteError.UnknownError, e.message))
        }
        catch (e: Exception){
            e.printStackTrace()
            return Result.Error(DataError.RemoteDataError(RemoteError.UnknownError, e.message))
        }
    }
}