package nl.codingwithlinda.notemark.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.core.domain.persistence.LocalAccess
import nl.codingwithlinda.notemark.BuildConfig
import nl.codingwithlinda.notemark.core.data.auth.register.KtorRegisterService
import nl.codingwithlinda.notemark.core.data.auth.register.RegisterService
import nl.codingwithlinda.notemark.core.data.auth.session.SessionStorageImpl
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSession
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSessionSerializer
import nl.codingwithlinda.notemark.core.data.remote.common.DefaultHttpClient
import nl.codingwithlinda.persistence_room.database.DataAccess
import nl.codingwithlinda.persistence_room.public_access.LocalNoteAccess


val Context.dataStoreLoginSession: DataStore<LoginSession> by dataStore("login_session.json", LoginSessionSerializer)

class NoteMarkApplication: Application() {

    private val auth_api_key = BuildConfig.AUTH_API_EMAIL

    companion object {
        lateinit var localNoteAccess: LocalNoteAccess
        lateinit var registerService: RegisterService
        val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    }
    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

        val db = DataAccess(this).db
        localNoteAccess = LocalNoteAccess(
            noteDatabase = db,
        )
        val loginSessionDataStore = this.dataStoreLoginSession
        val sessionStorage = SessionStorageImpl(loginSessionDataStore)
        val defaultHttpClient = DefaultHttpClient(sessionStorage)
        registerService = KtorRegisterService(defaultHttpClient.httpClient)

    }
}