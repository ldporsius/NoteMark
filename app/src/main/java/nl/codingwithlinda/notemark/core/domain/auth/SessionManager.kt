package nl.codingwithlinda.notemark.core.domain.auth

import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.notemark.core.data.auth.login.LoginRequestDto
import nl.codingwithlinda.notemark.core.data.auth.login.LoginResponseDto
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSession
import nl.codingwithlinda.notemark.core.domain.error.AuthError
import nl.codingwithlinda.notemark.core.domain.error.LoginError
import nl.codingwithlinda.notemark.core.util.Result

interface SessionManager {
    suspend fun login(request: LoginRequestDto): Result<LoginResponseDto, LoginError>
    suspend fun logout(): Boolean
    suspend fun isSessionValid(): Boolean
    val loginState : Flow<LoginSession>
}