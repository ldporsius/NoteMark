package nl.codingwithlinda.notemark.core.data.auth.session

import android.app.Application
import android.util.Log.e
import io.ktor.client.call.body
import io.ktor.client.engine.cio.FailToConnectException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.app.dataStoreLoginSession
import nl.codingwithlinda.notemark.core.data.auth.login.KtorLoginService
import nl.codingwithlinda.notemark.core.data.auth.login.LoginRequestDto
import nl.codingwithlinda.notemark.core.data.auth.login.LoginResponseDto
import nl.codingwithlinda.notemark.core.data.auth.login.LoginService
import nl.codingwithlinda.notemark.core.data.auth.logout.KtorLogoutService
import nl.codingwithlinda.notemark.core.data.auth.refresh.RefreshTokenDto
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSession
import nl.codingwithlinda.notemark.core.data.remote.common.DefaultHttpClient
import nl.codingwithlinda.notemark.core.data.remote.common.HttpRoutes
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.domain.auth.SessionStorage
import nl.codingwithlinda.notemark.core.domain.error.AuthError
import nl.codingwithlinda.notemark.core.domain.error.LoginError
import nl.codingwithlinda.notemark.core.domain.error.RemoteError
import nl.codingwithlinda.notemark.core.util.Result
import java.lang.System
import kotlin.coroutines.coroutineContext

class KtorSessionManager(
    application: Application
): SessionManager {
    private val loginSessionDataStore = application.dataStoreLoginSession
    private val sessionStorage = SessionStorageImpl(loginSessionDataStore)
    private val defaultHttpClient = DefaultHttpClient(sessionStorage)
    private val loginService = KtorLoginService(defaultHttpClient.httpClient)
    private val logoutService = KtorLogoutService(defaultHttpClient.httpClient, sessionStorage)

    override val loginState: Flow<LoginSession>
        get() = loginSessionDataStore.data

    override suspend fun login(request: LoginRequestDto): Result<LoginResponseDto, LoginError> {
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

        return Result.Error(LoginError.AuthLoginError(AuthError.UnknownError))
    }


    override suspend fun logout(): Result<Unit, RemoteError> {
        try {
            val logoutRes = logoutService.logout()
            when(logoutRes){
                is Result.Error -> {
                    return Result.Error(logoutRes.error)
                }
                is Result.Success -> {
                    deleteSession()
                    return Result.Success(Unit)
                }
            }

        }catch (e: Exception){
            println("We are in the catch block")
            coroutineContext.ensureActive()
            println("KTOR SESSION MANAGER ERROR MESSAGE: ${e.message}")
            return Result.Error(RemoteError.UnknownError)
        }
    }

    override suspend fun isSessionValid(): Boolean {
        //make request. if error code -> lookup error in enum class Autherror
        //if Autherror is InvalidCredentialsError -> refresh
        //if refresh return error -> session is expired
        // return session expired error

        try {
            val refreshToken = loginSessionDataStore.data.firstOrNull()?.refreshToken
            val response = defaultHttpClient.httpClient.post(
                urlString = HttpRoutes.REFRESH_URL,
            ) {
                contentType(ContentType.Application.Json)
                setBody(
                    RefreshTokenRequestDto(
                        refreshToken = refreshToken ?: ""
                    )
                )
            }
            val tokens = response.body<RefreshTokenDto>()
            loginSessionDataStore.updateData {
                it.copy(
                    accessToken = tokens.accessToken,
                    refreshToken = tokens.refreshToken
                )
            }
            return true
        }catch (e: FailToConnectException){
            e.printStackTrace()
            return true
        }
        catch (e: UnresolvedAddressException){
            e.printStackTrace()
            return true
        }
        catch (e: ResponseException){
            e.printStackTrace()
            val code = e.response.status
            val bodyTxt = e.response.bodyAsText()
            println("KTOR SESSION MANAGER RESPONSE ERROR MESSAGE: ${code}, ${bodyTxt}")
            return false
        }
        catch (e: Exception){
            e.printStackTrace()
            return false

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
                refreshToken = "",
                dateCreated = System.currentTimeMillis()
            )
        }
    }


}