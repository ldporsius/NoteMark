package nl.codingwithlinda.notemark.core.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import nl.codingwithlinda.notemark.core.data.auth.KtorApiClient
import nl.codingwithlinda.notemark.core.data.auth.KtorSessionManager
import nl.codingwithlinda.notemark.core.data.auth.SessionStorageImpl
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSession
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSessionSerializer
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager

val Context.dataStoreLoginSession: DataStore<LoginSession> by dataStore("login_session.json", LoginSessionSerializer)

class NoteMarkApplication: Application() {

    lateinit var loginSessionDataStore: DataStore<LoginSession>
    lateinit var sessionManager: SessionManager
    private val sessionStorage = SessionStorageImpl(loginSessionDataStore)
    private val authApiClient = KtorApiClient()
    override fun onCreate() {
        super.onCreate()

        loginSessionDataStore = this.dataStoreLoginSession
        sessionManager = KtorSessionManager(
            authApiClient = authApiClient,
            loginDataStore = sessionStorage
        )

    }
}