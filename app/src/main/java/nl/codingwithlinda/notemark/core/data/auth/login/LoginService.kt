package nl.codingwithlinda.notemark.core.data.auth.login

import nl.codingwithlinda.notemark.core.domain.error.AuthError
import nl.codingwithlinda.notemark.core.util.Result

interface LoginService {
    suspend fun login(email: String, password: String): Result<LoginResponseDto, AuthError>
}