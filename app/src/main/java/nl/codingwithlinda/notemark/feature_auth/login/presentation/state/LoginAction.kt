package nl.codingwithlinda.notemark.feature_auth.login.presentation.state

sealed interface LoginAction {
    data class EmailChanged(val email: String) : LoginAction
    data class PasswordChanged(val password: String) : LoginAction
    data object TogglePasswordVisibility : LoginAction
    data object Submit : LoginAction
    data object Cancel : LoginAction

}