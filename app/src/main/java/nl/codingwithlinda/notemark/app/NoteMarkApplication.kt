package nl.codingwithlinda.notemark.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.jakewharton.threetenabp.AndroidThreeTen
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSession
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSessionSerializer
import nl.codingwithlinda.notemark.core.di.AndroidAppModule
import nl.codingwithlinda.notemark.core.di.AppModule


val Context.dataStoreLoginSession: DataStore<LoginSession> by dataStore("login_session.json", LoginSessionSerializer)

class NoteMarkApplication: Application() {

    companion object {
        lateinit var appModule: AppModule

    }
    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

        appModule = AndroidAppModule(this)

    }
}