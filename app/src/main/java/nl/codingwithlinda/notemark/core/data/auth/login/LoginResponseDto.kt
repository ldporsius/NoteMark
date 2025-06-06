package nl.codingwithlinda.notemark.core.data.auth.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
)
