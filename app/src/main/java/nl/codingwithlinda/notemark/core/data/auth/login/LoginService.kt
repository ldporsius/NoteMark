package nl.codingwithlinda.notemark.core.data.auth.login

import nl.codingwithlinda.notemark.core.data.remote.common.DefaultHttpClient
import nl.codingwithlinda.notemark.core.domain.auth.AuthError
import nl.codingwithlinda.notemark.core.util.Result

interface LoginService {
    suspend fun login(email: String, password: String): Result<LoginResponseDto, AuthError>

    companion object {
        fun create(
        ): LoginService {
            return KtorLoginService(
                client = DefaultHttpClient.httpClient
            )
        }
    }
}