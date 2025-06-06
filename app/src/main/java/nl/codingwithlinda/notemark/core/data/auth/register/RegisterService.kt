package nl.codingwithlinda.notemark.core.data.auth.register

import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.internal.writeJson
import io.ktor.serialization.kotlinx.json.*
import nl.codingwithlinda.notemark.core.data.auth.common.DefaultHttpClient
import nl.codingwithlinda.notemark.core.domain.auth.AuthError
import nl.codingwithlinda.notemark.core.util.Result


interface RegisterService{

    suspend fun register(request: RegisterRequestDto): Result<Unit, AuthError>

    companion object {
        fun create(
            authorizer: String
        ): RegisterService {
            return KtorRegisterService(
                authorizer = authorizer,
                httpClient = DefaultHttpClient.httpClient
                /*httpClient = HttpClient(CIO) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(ContentNegotiation){
                        json()
                    }
                }*/
            )
        }
    }
}