package nl.codingwithlinda.notemark.core.data.auth

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.request.header
import io.ktor.client.request.post
import nl.codingwithlinda.notemark.core.domain.auth.AuthApiClient
import nl.codingwithlinda.notemark.core.domain.auth.AuthError
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_auth.login.domain.LoginValidator

class KtorApiClient(
    private val authorizerHeader: String
): AuthApiClient {

    private val httpClient = HttpClient {
        install(Auth) {
            // Configure your auth providers here
            this.bearer {
                loadTokens {
                    BearerTokens(
                        accessToken = "",
                        refreshToken = ""
                    )
                }
                refreshTokens {
                    BearerTokens(
                        accessToken = "",
                        refreshToken = ""
                    )
                }
            }
        }
    }

    override fun validateCredentials(userEmail: String, password: String): Boolean {
        val isEmail = LoginValidator.validateLoginEmail(userEmail) == null
        val isPassword = LoginValidator.validateLoginPassword(password) == null
        return isEmail && isPassword
    }

    override suspend fun login(request: LoginRequestDto): Result<LoginResponseDto, AuthError> {
        println("We are in the login function")
        httpClient.post {
            this.header("X-User-Email", authorizerHeader)

        }
        return Result.Error(AuthError.SessionExpiredError)
    }

    override suspend fun refreshToken(request: RefreshTokenRequestDto): Result<LoginResponseDto, AuthError> {
        println("We are in the refresh function")
        return Result.Error(AuthError.SessionExpiredError)
    }
}