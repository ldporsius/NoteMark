package nl.codingwithlinda.notemark.core.domain.auth

import nl.codingwithlinda.notemark.core.data.auth.login.LoginRequestDto
import nl.codingwithlinda.notemark.core.data.auth.login.LoginResponseDto
import nl.codingwithlinda.notemark.core.domain.error.AuthError
import nl.codingwithlinda.notemark.core.util.Result

interface SessionManager {
    suspend fun login(request: LoginRequestDto): Result<LoginResponseDto, AuthError>
    suspend fun logout(): Boolean
    suspend fun isSessionValid(): Boolean
}