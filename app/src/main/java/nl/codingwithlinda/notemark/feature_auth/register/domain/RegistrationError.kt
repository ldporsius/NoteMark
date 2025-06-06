package nl.codingwithlinda.notemark.feature_auth.register.domain

import nl.codingwithlinda.notemark.core.util.Error

interface RegistrationError: Error

sealed interface RegistrationUserNameError: RegistrationError {
    data object EmptyUsername : RegistrationUserNameError
    data object UsernameShort : RegistrationUserNameError
}
sealed interface RegistrationEmailError: RegistrationError {
    data object EmptyEmail : RegistrationEmailError
    data object InvalidEmail : RegistrationEmailError
}
sealed interface RegistrationPasswordError: RegistrationError {
    data object EmptyPassword : RegistrationPasswordError
    data object PasswordShort : RegistrationPasswordError
    data object PasswordInvalid: RegistrationPasswordError
    data object PasswordsNotMatching : RegistrationPasswordError

}