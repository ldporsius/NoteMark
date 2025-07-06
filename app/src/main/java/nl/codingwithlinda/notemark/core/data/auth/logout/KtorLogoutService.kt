package nl.codingwithlinda.notemark.core.data.auth.logout

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.firstOrNull
import nl.codingwithlinda.notemark.core.data.remote.common.HttpRoutes
import nl.codingwithlinda.notemark.core.domain.auth.SessionStorage
import nl.codingwithlinda.notemark.core.domain.error.RemoteError
import nl.codingwithlinda.notemark.core.util.Result

class KtorLogoutService(
    private val client: HttpClient,
    private val sessionStorage: SessionStorage
): LogoutService {
    override suspend fun logout(): Result<Unit, RemoteError> {
        try {
            val dto = LogoutRequestDto(
                refreshToken = sessionStorage.data.firstOrNull()?.refreshToken ?: return Result.Success(Unit)
            )
            client.post(HttpRoutes.LOGOUT_URL) {
                contentType(ContentType.Application.Json)
                setBody(dto)
            }

            return Result.Success(Unit)
        }
        catch (e: Exception){
            e.printStackTrace()

            return Result.Error(RemoteError.UnknownError)
        }
    }
}