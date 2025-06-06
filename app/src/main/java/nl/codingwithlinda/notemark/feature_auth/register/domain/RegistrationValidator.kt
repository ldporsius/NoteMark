package nl.codingwithlinda.notemark.feature_auth.register.domain

object RegistrationValidator {

    fun validateUsername(username: String): RegistrationUserNameError? {
        if (username.isBlank()) {
            return RegistrationUserNameError.EmptyUsername
        }
        if (username.length < 3) {
            return RegistrationUserNameError.UsernameShort
        }
        return null
    }
    fun validateEmail(email: String): RegistrationEmailError? {
        if (email.isBlank()) {
            return RegistrationEmailError.EmptyEmail
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return RegistrationEmailError.InvalidEmail
        }
        return null
    }
    fun validatePassword(password: String): RegistrationPasswordError? {
        //password must contain at least 1 letter and 1 number e.g. Aa123456
        //val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$".toRegex()
        if (password.isBlank()) {
            return RegistrationPasswordError.EmptyPassword
        }

        if (password.length < 3) {
            return RegistrationPasswordError.PasswordShort
        }
        //if (!password.matches(passwordRegex)) {
        //    return RegistrationPasswordError.PasswordInvalid
        //}
        return null
    }

    fun validateRepeatedPassword(password: String, repeatedPassword: String): RegistrationPasswordError? {
        if (password != repeatedPassword) {
            return RegistrationPasswordError.PasswordsNotMatching
        }
        return null

    }

}