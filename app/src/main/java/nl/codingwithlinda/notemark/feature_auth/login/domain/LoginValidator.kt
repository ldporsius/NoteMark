package nl.codingwithlinda.notemark.feature_auth.login.domain

object LoginValidator {

    fun validateLoginEmail(email: String): EmailError?  {
        if (email.isBlank()) {
            return EmailError.EmailBlankError
        }
        val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (!isValidEmail) {
            return EmailError.EmailInvalidError
        }
        return null

    }
}