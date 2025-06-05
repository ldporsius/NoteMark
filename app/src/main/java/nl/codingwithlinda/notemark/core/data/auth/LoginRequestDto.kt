package nl.codingwithlinda.notemark.core.data.auth

data class LoginRequestDto(
    val email: String,
    val password: String,
)
