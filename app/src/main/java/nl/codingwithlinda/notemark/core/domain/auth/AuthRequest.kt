package nl.codingwithlinda.notemark.core.domain.auth

data class AuthRequest(
    val email: String,
    val password: String,
)
