package nl.codingwithlinda.notemark.core.data.remote.common

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.buildHeaders
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import nl.codingwithlinda.notemark.BuildConfig
import nl.codingwithlinda.notemark.core.data.auth.refresh.RefreshTokenDto
import nl.codingwithlinda.notemark.core.data.auth.session.RefreshTokenRequestDto
import nl.codingwithlinda.notemark.core.domain.auth.SessionStorage

class DefaultHttpClient(
    private val loginDataStore: SessionStorage
) {

    val httpClient = HttpClient(CIO) {
        expectSuccess = true
        install(Logging) {
            level = LogLevel.ALL
        }
        install(DefaultRequest){
            buildHeaders(){
                header("Content-Type", "application/json")
                header("X-User-Email", BuildConfig.AUTH_API_EMAIL)

            }
        }
        install(Resources)
        install(ContentNegotiation){
            json()
        }
        install(Auth) {
            // Configure your auth providers here
            this.bearer {
                loadTokens {
                    val accessToken = loginDataStore.data.firstOrNull()?.accessToken
                    val refreshToken = loginDataStore.data.firstOrNull()?.refreshToken
                    BearerTokens(
                        accessToken = accessToken ?: "",
                        refreshToken = refreshToken ?: ""
                    )
                }

                refreshTokens {
                    val refreshToken = loginDataStore.data.firstOrNull()?.refreshToken
                    val response = client.post(
                        urlString = HttpRoutes.REFRESH_URL,
                    ) {
                        contentType(ContentType.Application.Json)
                        setBody(
                            RefreshTokenRequestDto(
                                refreshToken = refreshToken ?: ""
                            ))
                        markAsRefreshTokenRequest()
                    }


                    val tokens = response.body<RefreshTokenDto>()
                    loginDataStore.updateData {
                        it.copy(
                            accessToken = tokens.accessToken,
                            refreshToken = tokens.refreshToken
                        )
                    }

                    BearerTokens(
                        accessToken = tokens.accessToken,
                        refreshToken = tokens.refreshToken
                    )

                }
            }
        }
    }
}