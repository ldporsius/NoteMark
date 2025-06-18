package nl.codingwithlinda.notemark.core.domain.error

import nl.codingwithlinda.notemark.core.util.Error


sealed interface DataError: Error {
    data class RemoteDataError(val error: RemoteError): DataError
    data class LocalDataError(val error: LocalError): DataError
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