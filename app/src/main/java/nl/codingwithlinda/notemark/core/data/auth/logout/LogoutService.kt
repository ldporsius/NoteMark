package nl.codingwithlinda.notemark.core.data.auth.logout

import nl.codingwithlinda.notemark.core.domain.error.RemoteError
import nl.codingwithlinda.notemark.core.util.Result

interface LogoutService {

    suspend fun logout(): Result<Unit, RemoteError>
}