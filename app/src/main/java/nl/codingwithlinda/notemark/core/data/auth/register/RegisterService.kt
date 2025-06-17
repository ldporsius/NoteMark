package nl.codingwithlinda.notemark.core.data.auth.register

import nl.codingwithlinda.notemark.core.domain.error.AuthError
import nl.codingwithlinda.notemark.core.util.Result


interface RegisterService{
    suspend fun register(request: RegisterRequestDto): Result<Unit, AuthError>
}