package nl.codingwithlinda.notemark.core.data.auth.register

import io.ktor.client.*
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.buildHeaders
import io.ktor.http.ContentType
import io.ktor.http.contentType
import nl.codingwithlinda.notemark.core.data.auth.HttpRoutes

class KtorRegisterService(
    private val authorizer: String,
    private val httpClient: HttpClient
): RegisterService {
    override suspend fun register(request: RegisterRequestDto) {
        try {
            println("Inside KtorRegisterService. Posting request to ${HttpRoutes.REGISTER_URL}")
            println("Inside KtorRegisterService. X-User-Email: $authorizer")
            httpClient.post(HttpRoutes.REGISTER_URL){
                buildHeaders {
                    append("X-User-Email", authorizer)
                    append("Content-Type", "application/json")
                }
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }
        catch (e: Exception){
            println("We are in the catch block of KtorRegisterService")
            println("KTOR REGISTER SERVICE ERROR MESSAGE: ${e.message}")
        }
    }
}