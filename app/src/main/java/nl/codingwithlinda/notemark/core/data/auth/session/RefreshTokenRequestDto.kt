package nl.codingwithlinda.notemark.core.data.auth.session

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequestDto(
    val refreshToken: String
)
