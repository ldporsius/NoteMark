package nl.codingwithlinda.notemark.test_data_generators

import kotlinx.coroutines.delay
import nl.codingwithlinda.notemark.core.data.auth.LoginRequestDto
import nl.codingwithlinda.notemark.core.data.auth.LoginResponseDto
import nl.codingwithlinda.notemark.core.data.auth.RefreshTokenRequestDto
import nl.codingwithlinda.notemark.core.domain.auth.AuthApiClient
import nl.codingwithlinda.notemark.core.domain.auth.AuthError
import nl.codingwithlinda.notemark.core.util.Result

class FakeAuthClient(
    var isSuccessful: Boolean = true,
    var isError: Boolean = false
): AuthApiClient {

    fun throwException() {
        isError = true
    }
    override fun validateCredentials(userEmail: String, password: String): Boolean {
        val isEmail = userEmail.isNotBlank()
        val isPassword = password.isNotBlank()
        return isEmail && isPassword
    }
    override suspend fun login(request: LoginRequestDto): Result<LoginResponseDto, AuthError> {
        val isCredentialsValid = validateCredentials(request.email, request.password)
        if (!isCredentialsValid) {
            return Result.Error(AuthError.UserInputError)
        }
        delay(1000)
        if (isError) throw Exception("fake error")
        if (isSuccessful) {
            return Result.Success(
                LoginResponseDto(
                    accessToken = "access_token",
                    refreshToken = "refresh_token"
                )
            )
        }
        return Result.Error(AuthError.InvalidCredentialsError)
    }

    var isAccessTokenValid = true //expires after 15 minutes
    var isRefreshTokenValid = true //expires after 30 days
    var returnExceptionOnRefreshToken: () -> AuthError? = {null}
    var throwExceptionOnRefreshToken: () -> AuthError? = {null}

    override suspend fun refreshToken(request: RefreshTokenRequestDto): Result<LoginResponseDto, AuthError> {
        throwExceptionOnRefreshToken.invoke().let {
            if (it != null) {
                throw Exception("${it.code}")
            }
        }
        returnExceptionOnRefreshToken.invoke().let {
            if (it != null) {
                return Result.Error(it)
            }
        }

        if (isRefreshTokenValid) {
            return Result.Success(
                LoginResponseDto(
                    accessToken = "access_token",
                    refreshToken = "refresh_token"
                )
            )
        }
        if (!isAccessTokenValid) {
            return Result.Error(AuthError.InvalidCredentialsError)
        }
        return Result.Error(AuthError.SessionExpiredError)
    }
}