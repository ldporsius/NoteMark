package nl.codingwithlinda.notemark.core.data.auth

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSession
import nl.codingwithlinda.notemark.core.domain.auth.SessionStorage

class SessionStorageImpl(
    private val dataStore: DataStore<LoginSession>
): SessionStorage {
    override val data: Flow<LoginSession>
        get() = dataStore.data

    override suspend fun updateData(transform: suspend (t: LoginSession) -> LoginSession) {
        dataStore.updateData(transform)
    }
}