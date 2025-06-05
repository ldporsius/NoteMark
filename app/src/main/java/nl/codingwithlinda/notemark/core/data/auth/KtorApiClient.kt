package nl.codingwithlinda.notemark.core.data.auth

import nl.codingwithlinda.notemark.core.domain.auth.AuthApiClient
import nl.codingwithlinda.notemark.core.domain.auth.AuthError
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_auth.login.domain.LoginValidator

class KtorApiClient: AuthApiClient {
    override fun validateCredentials(userEmail: String, password: String): Boolean {
        val isEmail = LoginValidator.validateLoginEmail(userEmail) == null
        val isPassword = LoginValidator.validateLoginPassword(password) == null
        return isEmail && isPassword
    }

    override suspend fun login(request: LoginRequestDto): Result<LoginResponseDto, AuthError> {
        println("We are in the login function")
        return Result.Error(AuthError.SessionExpiredError)
    }

    override suspend fun refreshToken(request: RefreshTokenRequestDto): Result<LoginResponseDto, AuthError> {
        println("We are in the refresh function")
        return Result.Error(AuthError.SessionExpiredError)
    }
}