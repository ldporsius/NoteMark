package nl.codingwithlinda.notemark.feature_auth.login

object LoginValidator {

    fun validateLoginEmail(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}