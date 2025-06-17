package nl.codingwithlinda.notemark.core.data.auth.refresh

data class RefreshTokenDto(
    val accessToken: String,
    val refreshToken: String
)
