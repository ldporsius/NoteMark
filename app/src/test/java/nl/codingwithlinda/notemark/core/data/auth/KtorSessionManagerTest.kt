package nl.codingwithlinda.notemark.core.data.auth

import junit.framework.Assert.assertTrue
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.notemark.core.data.auth.login.LoginRequestDto
import nl.codingwithlinda.notemark.core.data.auth.session.KtorSessionManager
import nl.codingwithlinda.notemark.core.domain.auth.AuthError
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.test_data_generators.FakeAuthClient
import nl.codingwithlinda.notemark.test_data_generators.FakeSessionStorage
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class KtorSessionManagerTest {

    private val authApiClient = FakeAuthClient()
    private val sessionStorage = FakeSessionStorage()
    private val sessionManager = KtorSessionManager(
        authApiClient = authApiClient,
        loginDataStore = sessionStorage
    )

    @Before
    fun setup(){
        authApiClient.isSuccessful = true
        authApiClient.isError = false
        authApiClient.isAccessTokenValid = true
        authApiClient.isRefreshTokenValid = true
        authApiClient.throwExceptionOnRefreshToken = {null}
        authApiClient.returnExceptionOnRefreshToken = {null}
        sessionStorage.isError = false
        runBlocking {
            sessionManager.logout()
        }
    }

    @Test
    fun `login success`() = runBlocking{
        // Verify that a successful login attempt saves the session and returns a success result.
       val result = sessionManager.login(LoginRequestDto("username", "password"))
        assertTrue(sessionStorage.data.value.accessToken.isNotEmpty())
        assertTrue(sessionStorage.data.value.refreshToken.isNotEmpty())
        assertTrue(result is Result.Success)
    }

    @Test
    fun `login failure`() {
        // Verify that a failed login attempt does not save the session and returns an error result.
       authApiClient.isSuccessful = false
        val result = runBlocking {
            sessionManager.login(LoginRequestDto("username", "password"))
        }
        assertTrue(result is Result.Error)
        assertTrue(sessionStorage.data.value.accessToken.isEmpty())
        assertTrue(sessionStorage.data.value.refreshToken.isEmpty())
    }

    @Ignore
    @Test
    fun `login request null`() {
        // Test login with a null LoginRequestDto. Expect an appropriate error or exception.

    }

    @Test
    fun `login request empty credentials`() {
        // Test login with empty username/password in LoginRequestDto. Expect AuthError.
        val result = runBlocking {
            sessionManager.login(LoginRequestDto("", ""))
        }
        assertTrue(result is Result.Error)
    }

    @Test
    fun `login request invalid credentials`() {
        // Test login with incorrect username/password. Expect AuthError.
        // TODO implement test
    }

    @Test
    fun `login api client exception`(): Unit = runBlocking{
        // Mock AuthApiClient to throw an exception during login. Verify error handling.
        launch {
            authApiClient.throwException()
            println("Error thrown")
        }
        val result = launch {
            try {
                sessionManager.login(LoginRequestDto("username", "password"))
            }catch (e: Exception){
                println("We are in the test catch block. error is ${e::class.java}")
                assertTrue(sessionStorage.data.value.accessToken.isEmpty())
                assertTrue(sessionStorage.data.value.refreshToken.isEmpty())
            }
        }

        result.join()
        assertTrue(sessionStorage.data.value.accessToken.isEmpty())
        assertTrue(sessionStorage.data.value.refreshToken.isEmpty())
    }

    @Test
    fun `login data store failure during save`() {
        // Mock loginDataStore.updateData to throw an exception during saveSession. Verify that the login result is still returned, but the error is logged or handled.
        sessionStorage.throwException()
        val result = runBlocking {
            sessionManager.login(LoginRequestDto("username", "password"))
        }
        assertTrue(result is Result.Error)
    }

    @Test
    fun `logout success`() {
        // Verify that logout successfully deletes the session.
        runBlocking {
            sessionManager.logout()
        }
        assertTrue(sessionStorage.data.value.accessToken.isEmpty())
        assertTrue(sessionStorage.data.value.refreshToken.isEmpty())
    }

    @Test
    fun `logout data store failure`() {
        // Mock loginDataStore.updateData to throw an exception during deleteSession. Verify error handling.
        runBlocking {
            sessionStorage.throwException()
            sessionManager.logout()

            assertTrue(sessionStorage.data.value.accessToken.isEmpty())
            assertTrue(sessionStorage.data.value.refreshToken.isEmpty())
        }
    }

    @Test
    fun `logout when no session exists`() {
        // Verify that calling logout when no session data is present completes without error and effectively does nothing or clears empty data.
        runBlocking {
            sessionManager.logout()

            assertTrue(sessionStorage.data.value.accessToken.isEmpty())
            assertTrue(sessionStorage.data.value.refreshToken.isEmpty())
        }
    }

    @Test
    fun `isSessionValid with actual validation logic _ valid session`(): Unit = runBlocking {
        // Mock an API call that indicates a valid session. Expect true.
        val isValid = sessionManager.isSessionValid()
        assertTrue(isValid)
    }

    @Test
    fun `isSessionValid with actual validation logic   invalid credentials leading to refresh success`(): Unit = runBlocking  {
        // Mock API to return InvalidCredentialsError. Mock refresh to succeed. Expect true and session to be updated.
        authApiClient.isAccessTokenValid = false
        val isValid = sessionManager.isSessionValid()
        assertTrue(isValid)

        assertTrue(sessionStorage.data.value.accessToken.isNotEmpty())
        assertTrue(sessionStorage.data.value.refreshToken.isNotEmpty())
    }

    @Test
    fun `isSessionValid with actual validation logic   invalid credentials leading to refresh failure`() {
        // Mock API to return InvalidCredentialsError. Mock refresh to fail. Expect false.
        runBlocking {
            authApiClient.isAccessTokenValid = false
            authApiClient.isRefreshTokenValid = false
            val isValid = sessionManager.isSessionValid()
            assertTrue(!isValid)

            assertTrue(sessionStorage.data.value.accessToken.isEmpty())
            assertTrue(sessionStorage.data.value.refreshToken.isEmpty())
        }
    }

    @Test
    fun `isSessionValid with actual validation logic   other auth error`() {
        // (Future Test) Mock API to return an AuthError other than InvalidCredentialsError. Expect false.
        runBlocking {
            authApiClient.returnExceptionOnRefreshToken = {
                AuthError.ConflictError
            }
            authApiClient.isAccessTokenValid = false
            authApiClient.isRefreshTokenValid = false
            val isValid = sessionManager.isSessionValid()
            assertTrue(!isValid)

            assertTrue(sessionStorage.data.value.accessToken.isEmpty())
            assertTrue(sessionStorage.data.value.refreshToken.isEmpty())
        }
    }

    @Test
    fun `isSessionValid with actual validation logic   API client exception`() {
        // (Future Test) Mock the underlying API client in isSessionValid to throw an exception. Expect false or appropriate error handling.
        runBlocking {
            authApiClient.throwExceptionOnRefreshToken = {
                AuthError.ConflictError
            }
            authApiClient.isAccessTokenValid = false
            authApiClient.isRefreshTokenValid = false
            val isValid = sessionManager.isSessionValid()
            assertTrue(!isValid)

            assertTrue(sessionStorage.data.value.accessToken.isEmpty())
            assertTrue(sessionStorage.data.value.refreshToken.isEmpty())
        }
    }

    @Test
    fun `refresh success`() {
        // Mock loginDataStore to provide a refresh token. Mock AuthApiClient.refreshToken to succeed. Verify session is updated and success result is returned.
        // TODO implement test
    }

    @Test
    fun `refresh failure   no refresh token in datastore`() {
        // Mock loginDataStore.data.firstOrNull() to return null. Expect Result.Error(AuthError.SessionExpiredError).
        // TODO implement test
    }

    @Test
    fun `refresh failure   empty refresh token in datastore`() {
        // Mock loginDataStore to provide an empty refresh token. Expect Result.Error(AuthError.SessionExpiredError).
        // TODO implement test
    }

    @Test
    fun `refresh failure   api client error`() {
        // Mock loginDataStore to provide a refresh token. Mock AuthApiClient.refreshToken to return an error. Verify session is not updated and error result is returned.
        // TODO implement test
    }

    @Test
    fun `refresh failure   api client exception`() {
        // Mock AuthApiClient.refreshToken to throw an exception. Verify error handling and appropriate result.
        // TODO implement test
    }

    @Test
    fun `refresh data store failure during save`() {
        // Mock loginDataStore.updateData to throw an exception during saveSession after a successful refresh. 
        // Verify that the refresh result (success) is still returned, but the error is logged or handled.
        // TODO implement test
    }

    @Test
    fun `saveSession updates datastore correctly`() {
        // Provide a LoginResponseDto and verify that loginDataStore.updateData is called with the correct access and refresh tokens.
        // TODO implement test
    }

    @Test
    fun `deleteSession clears datastore correctly`() {
        // Verify that loginDataStore.updateData is called to set access and refresh tokens to empty strings.
        // TODO implement test
    }

    @Test
    fun `concurrent login calls`() {
        // Test behavior when multiple login requests are made concurrently. Ensure data integrity and correct session handling.
        // TODO implement test
    }

    @Test
    fun `concurrent logout and login`() {
        // Test behavior when logout is called while a login is in progress, or vice-versa. Ensure consistent state.
        // TODO implement test
    }

    @Test
    fun `concurrent isSessionValid and login logout`() {
        // (Future Test) Test interactions between isSessionValid and login/logout operations when called concurrently.
        // TODO implement test
    }

    @Test
    fun `login with extremely long tokens`() {
        // Test if the system handles very long access/refresh tokens in LoginResponseDto correctly during saveSession, without truncation or errors if applicable.
        // TODO implement test
    }

    @Test
    fun `login response with null tokens`() {
        // Test how saveSession handles a LoginResponseDto where accessToken or refreshToken might be null (if the DTO allows it). 
        // It should ideally handle this gracefully, perhaps by storing empty strings or throwing a specific error.
        // TODO implement test
    }

}