package nl.codingwithlinda.notemark.core.data.auth

import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.firstOrNull
import nl.codingwithlinda.notemark.core.data.auth.login.LoginRequestDto
import nl.codingwithlinda.notemark.core.data.auth.login.LoginResponseDto
import nl.codingwithlinda.notemark.core.domain.auth.AuthApiClient
import nl.codingwithlinda.notemark.core.domain.auth.AuthError
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.domain.auth.SessionStorage
import nl.codingwithlinda.notemark.core.util.Result
import kotlin.coroutines.coroutineContext

class KtorSessionManager(
    private val authApiClient: AuthApiClient,
    private val loginDataStore: SessionStorage
): SessionManager {

    override suspend fun login(request: LoginRequestDto): Result<LoginResponseDto, AuthError> {
        try {
           authApiClient.login(request).also {res ->
                when(res){
                    is Result.Error -> {
                        println("${res.error}")
                    }
                    is Result.Success ->{
                        saveSession(res.data)}
                }
                return res
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
        refresh().also {
            if(it is Result.Error){
                println("We are in the isSessionValid error block. Error is ${it.error}, code is ${it.error.code}")
                return false
            }
        }
        return true
    }

    private suspend fun refresh():Result<LoginResponseDto, AuthError>{
        val oldRefreshToken = loginDataStore.data.firstOrNull()?.refreshToken ?: return Result.Error(AuthError.SessionExpiredError)
        try {

            authApiClient.refreshToken(
                RefreshTokenRequestDto(oldRefreshToken)
            ).also {
                if (it is Result.Success) {
                    println("We are in the refresh success block")
                    saveSession(it.data)
                    println("Session saved successfully")
                }
                if (it is Result.Error) {

                    println("We are in the refresh error block. Error is ${it.error}, code is ${it.error.code}")
                    //if we get a 401 the session is expired. logout and return error
                    if (it.error == AuthError.InvalidCredentialsError) {
                        logout()
                    }
                }
                return it
            }
        }catch (e: Exception){
            coroutineContext.ensureActive()
            return Result.Error(AuthError.UnknownError)
        }
    }

    private suspend fun saveSession(response: LoginResponseDto){
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