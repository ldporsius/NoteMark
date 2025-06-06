package nl.codingwithlinda.notemark.core.data.auth.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val username: String,
    val email: String,
    val password: String
)
