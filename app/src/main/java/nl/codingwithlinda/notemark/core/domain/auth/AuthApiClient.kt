package nl.codingwithlinda.notemark.core.domain.auth

import nl.codingwithlinda.notemark.core.data.auth.login.LoginRequestDto
import nl.codingwithlinda.notemark.core.data.auth.login.LoginResponseDto
import nl.codingwithlinda.notemark.core.data.auth.session.RefreshTokenRequestDto
import nl.codingwithlinda.notemark.core.util.Result

interface AuthApiClient {
    fun validateCredentials(userEmail: String, password: String): Boolean
    suspend fun login(request: LoginRequestDto): Result<LoginResponseDto, AuthError>
    suspend fun refreshToken(request: RefreshTokenRequestDto): Result<LoginResponseDto, AuthError>


}