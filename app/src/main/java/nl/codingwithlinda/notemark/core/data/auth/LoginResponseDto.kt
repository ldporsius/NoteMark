package nl.codingwithlinda.notemark.core.data.auth

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
)
