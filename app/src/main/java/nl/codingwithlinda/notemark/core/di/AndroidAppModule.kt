package nl.codingwithlinda.notemark.core.di

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import nl.codingwithlinda.notemark.app.dataStoreLoginSession
import nl.codingwithlinda.notemark.core.data.auth.register.KtorRegisterService
import nl.codingwithlinda.notemark.core.data.auth.session.SessionStorageImpl
import nl.codingwithlinda.notemark.core.data.remote.common.DefaultHttpClient
import nl.codingwithlinda.notemark.core.navigation.nav_intent_handler.NavigationIntentHandler
import nl.codingwithlinda.notemark.feature_home.data.local.NoteRepositoryImpl
import nl.codingwithlinda.notemark.feature_home.data.remote.NotesService
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.persistence_room.database.DataAccess
import nl.codingwithlinda.persistence_room.public_access.LocalNoteAccess

class AndroidAppModule(
    private val application: Application
): AppModule {

    private val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    val db = DataAccess(application).db
    val localNoteAccess = LocalNoteAccess(
        noteDatabase = db,
    )
    val loginSessionDataStore = application.dataStoreLoginSession
    val sessionStorage = SessionStorageImpl(loginSessionDataStore)
    val defaultHttpClient = DefaultHttpClient(sessionStorage)

    val remoteNoteAccess = NotesService(defaultHttpClient.httpClient)

    override val noteRepository: NoteRepository
        get() = NoteRepositoryImpl(
            applicationScope = applicationScope,
            noteAccess = localNoteAccess,
            remoteNoteAccess = remoteNoteAccess
        )

    override val registerService = KtorRegisterService(defaultHttpClient.httpClient)

    override val navIntentHandler: NavigationIntentHandler
        get() = NavigationIntentHandler(applicationScope)
}