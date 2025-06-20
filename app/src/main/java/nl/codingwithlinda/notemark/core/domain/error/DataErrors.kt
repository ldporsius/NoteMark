package nl.codingwithlinda.notemark.core.domain.error

import nl.codingwithlinda.notemark.core.util.Error


sealed interface DataError: Error {
    data class RemoteDataError(val error: RemoteError, val msg: String? = null): DataError
    data class LocalDataError(val error: LocalError): DataError
}

sealed interface LoginError: Error {
    data class AuthLoginError(val error: AuthError, val msg: String? = null): LoginError
    data class ConnectivityLoginError(val error: ConnectivityError): LoginError
}
enum class ConnectivityError: Error{
    NoConnectivityError,
    UnknownError
}
enum class AuthError(val code: Int): Error {
    BadRequestError(code = 400),
    InvalidCredentialsError(code = 401),
    RefreshTokenExpiredError(code = 401),
    InternalServerError(code = 500),
    MethodNotAllowedError(code = 405),
    ConflictError(code = 409),
    ToManyRequestsError(code = 429),
    SessionExpiredError(code = -1),
    UnknownError(code = -2)
}

enum class RemoteError(val code: Int): Error {
    BadRequestError(code = 400),
    InternalServerError(code = 500),
    MethodNotAllowedError(code = 405),
    ToManyRequestsError(code = 429),
    UnknownError(code = -2)
}

enum class LocalError(): Error {
    UnknownError,
    DISK_FULL,
    NOT_FOUND,
}