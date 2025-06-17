package nl.codingwithlinda.notemark.core.data.local_cache.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginSession(
    val userId: String = "",
    val accessToken: String = "",
    val refreshToken: String = "",
    val dateCreated: Long = System.currentTimeMillis()
)