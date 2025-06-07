package nl.codingwithlinda.notemark.feature_auth.register.domain

object RegistrationValidator {

    const val MIN_USERNAME_LENGTH = 3
    const val MAX_USERNAME_LENGTH = 20
    const val MIN_PASSWORD_LENGTH = 8

    fun validateUsername(username: String): RegistrationUserNameError? {
        if (username.isBlank()) {
            return null
        }
        if (username.length < MIN_USERNAME_LENGTH) {
            return RegistrationUserNameError.UsernameShort
        }
        if (username.length > MAX_USERNAME_LENGTH) {
            return RegistrationUserNameError.UsernameLong
        }
        return null
    }
    fun validateEmail(email: String): RegistrationEmailError? {
        if (email.isBlank()) {
            return null
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return RegistrationEmailError.InvalidEmail
        }
        return null
    }
    fun validatePassword(password: String): RegistrationPasswordError? {

        // Password must contain at least one digit or special character - Claude AI 2025-06
        val hasDigitOrSpecialChar = Regex("""[\d@$!%*#?&]""")

        if (password.isBlank()) {
            return null
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            return RegistrationPasswordError.PasswordShort
        }
        if (!hasDigitOrSpecialChar.containsMatchIn(password)) {
            return RegistrationPasswordError.PasswordInvalid
        }
        return null
    }

    fun validateRepeatedPassword(password: String, repeatedPassword: String): RegistrationPasswordError? {
        if (password != repeatedPassword) {
            return RegistrationPasswordError.PasswordsNotMatching
        }
        return null
    }
}