package nl.codingwithlinda.notemark.core.data.auth.refresh

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenDto(
    val accessToken: String,
    val refreshToken: String
)
