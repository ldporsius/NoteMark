package nl.codingwithlinda.notemark.core.data.auth.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String,
)
