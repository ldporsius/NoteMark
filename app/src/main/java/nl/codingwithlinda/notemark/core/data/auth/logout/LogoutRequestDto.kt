package nl.codingwithlinda.notemark.core.data.auth.logout

import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequestDto(
    val refreshToken: String
)
