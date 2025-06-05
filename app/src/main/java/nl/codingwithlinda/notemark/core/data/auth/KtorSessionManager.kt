package nl.codingwithlinda.notemark.core.data.auth

import androidx.datastore.core.DataStore
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSession
import nl.codingwithlinda.notemark.core.domain.auth.AuthError
import nl.codingwithlinda.notemark.core.domain.auth.AuthRequest
import nl.codingwithlinda.notemark.core.domain.auth.AuthResponse
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.util.Result

class KtorSessionManager(
    private val loginDataStore: DataStore<LoginSession>
): SessionManager {

    val baseUrl = "https://notemark.pl-coding.com"
    val loginEndpoint = "/api/auth/login"
    val refreshEndpoint = "/api/auth/refresh"

    override fun login(request: AuthRequest): Result<AuthResponse, AuthError> {

        //make request. if error code -> lookup error in enum class Autherror
        //if Autherror is InvalidCredentialsError -> refresh
        //if refresh return error -> session is expired
        // return session expired error
        return Result.Success(
            data = AuthResponse(
                accessToken = "test",
                refreshToken = "test"
            )
        )
    }

    private fun refresh():Result<AuthResponse, AuthError>{
        return Result.Success(
            data = AuthResponse(
                accessToken = "test",
                refreshToken = "test"
            )
        )
    }

    private suspend fun saveSession(response: AuthResponse){
        loginDataStore.updateData {
            it.copy(
                accessToken = response.accessToken,
                refreshToken = response.refreshToken
            )

        }
    }

    private suspend fun deleteSession(){
        loginDataStore.updateData {
            it.copy(
                accessToken = "",
                refreshToken = ""
            )
        }
    }


}