package nl.codingwithlinda.notemark.feature_auth.login.presentation.error_ui

import nl.codingwithlinda.notemark.core.util.UiText
import nl.codingwithlinda.notemark.feature_auth.login.domain.EmailError
import nl.codingwithlinda.notemark.feature_auth.login.domain.PasswordError

fun EmailError.toUi(): UiText{
    return when(this){
        EmailError.EmailBlankError -> UiText.DynamicText("Email cannot be blank")
        EmailError.EmailInvalidError -> UiText.DynamicText("Email is invalid")
    }
}

fun PasswordError.toUi(): UiText {
    return when (this) {
        PasswordError.PasswordBlankError -> UiText.DynamicText("Password cannot be blank")
        PasswordError.PasswordInvalidError -> UiText.DynamicText("Password is invalid")
    }
}