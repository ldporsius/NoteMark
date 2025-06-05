package nl.codingwithlinda.notemark.core.domain.auth

import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSession

interface SessionStorage {
    val data: Flow<LoginSession>
    suspend fun updateData(transform: suspend (t: LoginSession) -> LoginSession)
}