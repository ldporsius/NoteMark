package nl.codingwithlinda.core.domain.persistence

import kotlinx.coroutines.flow.Flow

interface LocalAccess<E, KEY> {
    suspend fun create(entity: E)
    suspend fun readByKey(filter: KEY): E?
    suspend fun update(entity: E)
    suspend fun delete(id: KEY)
    suspend fun deleteAll()
    val readAllFlow: Flow<List<E>>
}