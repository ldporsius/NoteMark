package nl.codingwithlinda.notemark.core.domain.auth

import nl.codingwithlinda.notemark.core.util.Error

enum class AuthError(code: Int): Error {
    InvalidCredentialsError(code = 401),
    RefreshTokenExpiredError(code = 401),
    SessionExpiredError(code = -1),

}