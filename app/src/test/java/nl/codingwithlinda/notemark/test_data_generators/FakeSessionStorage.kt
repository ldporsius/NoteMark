package nl.codingwithlinda.notemark.test_data_generators

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.notemark.core.data.local_cache.auth.LoginSession
import nl.codingwithlinda.notemark.core.domain.auth.SessionStorage

class FakeSessionStorage(
    var isError: Boolean = false
): SessionStorage {
    fun throwException() {
        isError = true
    }
    private val storage = MutableStateFlow(LoginSession())
    override val data: StateFlow<LoginSession>
        get() = storage

    override suspend fun updateData(transform: suspend (t: LoginSession) -> LoginSession) {
        if (isError) throw Exception("FakeSessionStorage Error")
        storage.update{
            transform(it)
        }
    }
}