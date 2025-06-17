package nl.codingwithlinda.notemark.core.data.auth.login

import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.post
import io.ktor.client.call.body
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import nl.codingwithlinda.notemark.core.data.remote.common.HttpRoutes
import nl.codingwithlinda.notemark.core.domain.error.AuthError
import nl.codingwithlinda.notemark.core.util.Result

class KtorLoginService(
    private val client: HttpClient
): LoginService {
    override suspend fun login(email: String, password: String): Result<LoginResponseDto, AuthError> {
        println("KTOR LOGIN SERVICE IS CALLED with email: $email and password: $password")
        try {
            val response = client.post(HttpRoutes.LOGIN_URL) {
                contentType(ContentType.Application.Json)
                setBody(LoginRequestDto(email, password))
            }

            if (response.status.value == 401)
            {
                println("KTOR LOGIN SERVICE RESPONDED CREDENTIALS ARE INVALID: ${response}")
                return Result.Error(AuthError.InvalidCredentialsError)
            }
            val dto = response.body<LoginResponseDto>()
            return Result.Success(dto)
        } catch (e: ResponseException) {
            val code = e.response.status.value
            println("KTOR LOGIN SERVICE HAS ERROR: ${code}, ${e.response.status.description}")
            AuthError.entries.find {
                it.code == code
            }?.let {
                return Result.Error(it)
            }
            return Result.Error(AuthError.UnknownError)
        }
        catch (e: NoTransformationFoundException) {
            println("KTOR LOGIN SERVICE HAS NO TRANSFORMATION FOR TYPE: ${e.message}")
            return Result.Error(AuthError.UnknownError)
        }
        catch (e: Exception) {
            println("KTOR LOGIN SERVICE HAS ERROR: ${e.message}")
            return Result.Error(AuthError.UnknownError)
        }
    }
}