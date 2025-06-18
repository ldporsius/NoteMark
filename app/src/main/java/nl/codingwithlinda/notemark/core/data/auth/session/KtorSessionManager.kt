package nl.codingwithlinda.notemark.core.data.auth.session

import android.app.Application
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import nl.codingwithlinda.notemark.app.dataStoreLoginSession
import nl.codingwithlinda.notemark.core.data.auth.login.KtorLoginService
import nl.codingwithlinda.notemark.core.data.auth.login.LoginRequestDto
import nl.codingwithlinda.notemark.core.data.auth.login.LoginResponseDto
import nl.codingwithlinda.notemark.core.data.auth.login.LoginService
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSession
import nl.codingwithlinda.notemark.core.data.remote.common.DefaultHttpClient
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.domain.auth.SessionStorage
import nl.codingwithlinda.notemark.core.domain.error.AuthError
import nl.codingwithlinda.notemark.core.util.Result
import kotlin.coroutines.coroutineContext

class KtorSessionManager(
    application: Application
): SessionManager {
    private val loginSessionDataStore = application.dataStoreLoginSession
    private val sessionStorage = SessionStorageImpl(loginSessionDataStore)
    private val defaultHttpClient = DefaultHttpClient(sessionStorage)
    private val loginService = KtorLoginService(defaultHttpClient.httpClient)
    private val SESSION_EXPIRATION_TIME = 15 * 60 * 1000 //15 MIN.

    override val loginState: Flow<LoginSession>
        get() = loginSessionDataStore.data
    override suspend fun login(request: LoginRequestDto): Result<LoginResponseDto, AuthError> {
        try {
            val loginRes = loginService.login(request.email, request.password)
            when(loginRes){
                is Result.Error -> {
                    return Result.Error(loginRes.error)
                }
                is Result.Success -> {
                    saveSession(loginRes.data)
                    return Result.Success(loginRes.data)
                }
            }
        }catch (e: Exception){
            println("We are in the catch block")
            coroutineContext.ensureActive()
            println("KTOR SESSION MANAGER ERROR MESSAGE: ${e.message}")
        }

        return Result.Error(AuthError.UnknownError)
    }


    override suspend fun logout(): Boolean {
        try {
            deleteSession()
            return true
        }catch (e: Exception){
            println("We are in the catch block")
            coroutineContext.ensureActive()
            println("KTOR SESSION MANAGER ERROR MESSAGE: ${e.message}")
            return false
        }
    }

    override suspend fun isSessionValid(): Boolean {
        //make request. if error code -> lookup error in enum class Autherror
        //if Autherror is InvalidCredentialsError -> refresh
        //if refresh return error -> session is expired
        // return session expired error
        val now = System.currentTimeMillis()
        val session = loginSessionDataStore.data.firstOrNull() ?: return false
        val isExpired = (now - session.dateCreated) > SESSION_EXPIRATION_TIME
        return !isExpired
    }

    private suspend fun refresh():Result<LoginResponseDto, AuthError>{
        val oldRefreshToken = loginSessionDataStore.data.firstOrNull()?.refreshToken ?: return Result.Error(
            AuthError.SessionExpiredError)
        try {
            return Result.Error(AuthError.UnknownError)
        }catch (e: Exception){
            coroutineContext.ensureActive()
            return Result.Error(AuthError.UnknownError)
        }
    }

    private suspend fun saveSession(response: LoginResponseDto){
        loginSessionDataStore.updateData {
            it.copy(
                userId = response.username,
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
                dateCreated = System.currentTimeMillis()
            )
        }
    }

    private suspend fun deleteSession(){
        loginSessionDataStore.updateData {
            it.copy(
                accessToken = "",
                refreshToken = ""
            )
        }
    }


}