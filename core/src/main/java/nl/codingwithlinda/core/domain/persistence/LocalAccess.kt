package nl.codingwithlinda.core.domain.persistence

import kotlinx.coroutines.flow.Flow

interface LocalAccess<E, KEY> {
    fun create(entity: E)
    fun readByKey(filter: KEY): E
    fun update(entity: E)
    fun delete(entity: E)
    val readAllFlow: Flow<List<E>>
}