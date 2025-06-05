package nl.codingwithlinda.notemark.core.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSession
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSessionSerializer

val Context.dataStoreLoginSession: DataStore<LoginSession> by dataStore("login_session.json", LoginSessionSerializer)

class NoteMarkApplication: Application() {

    lateinit var loginSessionDataStore: DataStore<LoginSession>
    override fun onCreate() {
        super.onCreate()

        loginSessionDataStore = this.dataStoreLoginSession
    }
}