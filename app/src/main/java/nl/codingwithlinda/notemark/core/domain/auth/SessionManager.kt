package nl.codingwithlinda.notemark.core.domain.auth

import nl.codingwithlinda.notemark.core.util.Result

interface SessionManager {
    fun login(
        request: AuthRequest
    ): Result<AuthResponse, AuthError>
}