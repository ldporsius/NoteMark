package nl.codingwithlinda.notemark.core.data.auth.register

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.client.utils.buildHeaders
import io.ktor.http.ContentType
import io.ktor.http.contentType
import nl.codingwithlinda.notemark.core.data.remote.common.HttpRoutes
import nl.codingwithlinda.notemark.core.domain.auth.AuthError
import nl.codingwithlinda.notemark.core.util.Result

class KtorRegisterService(
    private val authorizer: String,
    private val httpClient: HttpClient
): RegisterService {
    override suspend fun register(request: RegisterRequestDto): Result<Unit, AuthError> {
        try {
            println("Inside KtorRegisterService. Posting request to ${HttpRoutes.REGISTER_URL}")
            println("Inside KtorRegisterService. X-User-Email: $authorizer")
            val response = httpClient.post(HttpRoutes.REGISTER_URL){
                buildHeaders {
                    append("X-User-Email", authorizer)
                    append("Content-Type", "application/json")
                }
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            println("Inside KtorRegisterService. Response: ${response.bodyAsText()}")

            println("Inside KtorRegisterService. Response: code = ${response.status.value}, ${response.status.description}")

        }
        catch (e: ResponseException){
            println("Inside KtorRegisterService error catch. error: ${e.response.status.description}")
            val code = e.response.status.value
            AuthError.entries.find {
                it.code == code
            }?.let {
                return Result.Error(it)
            }
        }
       /* catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")

        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")

        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")

        }*/
        catch (e: Exception){
            println("We are in the catch block of KtorRegisterService")
            println("KTOR REGISTER SERVICE ERROR MESSAGE: ${e.message}")
            return Result.Error(AuthError.UnknownError)
        }
        return Result.Success(Unit)

    }
}