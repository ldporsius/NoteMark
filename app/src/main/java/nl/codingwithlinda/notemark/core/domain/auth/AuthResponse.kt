package nl.codingwithlinda.notemark.core.domain.auth

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
)
