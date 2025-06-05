package nl.codingwithlinda.notemark.core.domain.auth

import nl.codingwithlinda.notemark.core.util.Error

enum class AuthError(val code: Int): Error {
    UserInputError(code = 0),
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
enum class AuthUserError: Error {

}