package nl.codingwithlinda.notemark.feature_auth.login.domain

import nl.codingwithlinda.notemark.core.util.Error

sealed interface EmailError: Error {
    data object EmailBlankError: EmailError
    data object EmailInvalidError: EmailError
}

sealed interface PasswordError: Error {
    data object PasswordBlankError: PasswordError
    data object PasswordInvalidError:PasswordError
}