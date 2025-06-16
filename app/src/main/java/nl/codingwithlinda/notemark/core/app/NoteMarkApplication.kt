package nl.codingwithlinda.notemark.core.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.core.domain.persistence.LocalAccess
import nl.codingwithlinda.notemark.core.data.auth.KtorApiClient
import nl.codingwithlinda.notemark.core.data.auth.KtorSessionManager
import nl.codingwithlinda.notemark.core.data.auth.SessionStorageImpl
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSession
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSessionSerializer
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.BuildConfig
import nl.codingwithlinda.notemark.core.data.auth.login.LoginService
import nl.codingwithlinda.persistence_room.database.DataAccess
import nl.codingwithlinda.persistence_room.public_access.LocalNoteAccess

val Context.dataStoreLoginSession: DataStore<LoginSession> by dataStore("login_session.json", LoginSessionSerializer)

class NoteMarkApplication: Application() {

    lateinit var loginSessionDataStore: DataStore<LoginSession>
    lateinit var sessionManager: SessionManager
    lateinit var localNoteAccess: LocalAccess<Note, String>
    private val sessionStorage = SessionStorageImpl(loginSessionDataStore)
    private val auth_api_key = BuildConfig.AUTH_API_EMAIL
    private val authApiClient = KtorApiClient(authorizerHeader = auth_api_key)
    private val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val db = DataAccess(this).db
    companion object {
        val loginService = LoginService.create()
    }
    override fun onCreate() {
        super.onCreate()
        localNoteAccess = LocalNoteAccess(
            noteDatabase = db,
        )
        loginSessionDataStore = this.dataStoreLoginSession
        sessionManager = KtorSessionManager(
            authApiClient = authApiClient,
            loginDataStore = sessionStorage
        )

    }
}