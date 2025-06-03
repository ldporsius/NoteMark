package nl.codingwithlinda.notemark.feature_auth.login.state

sealed interface LoginAction {
    data class EmailChanged(val email: String) : LoginAction
    data class PasswordChanged(val password: String) : LoginAction
    object Submit : LoginAction

}