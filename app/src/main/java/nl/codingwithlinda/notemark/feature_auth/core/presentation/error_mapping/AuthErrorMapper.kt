package nl.codingwithlinda.notemark.feature_auth.core.presentation.error_mapping

import nl.codingwithlinda.notemark.core.domain.auth.AuthError
import nl.codingwithlinda.notemark.core.util.UiText

fun AuthError.toUiText() : UiText{
    return when(this){
        AuthError.InvalidCredentialsError -> UiText.DynamicText("Invalid credentials")
        AuthError.SessionExpiredError -> UiText.DynamicText("Session expired")
        AuthError.UnknownError -> UiText.DynamicText("Unknown error")
        AuthError.BadRequestError -> UiText.DynamicText("Bad request")
        AuthError.RefreshTokenExpiredError -> UiText.DynamicText("Refresh token expired")
        AuthError.InternalServerError -> UiText.DynamicText("Internal server error")
        AuthError.MethodNotAllowedError -> UiText.DynamicText("Method not allowed")
        AuthError.ConflictError -> UiText.DynamicText("A user with this email already exists")
        AuthError.ToManyRequestsError -> UiText.DynamicText("Too many requests")
    }
}