package nl.codingwithlinda.notemark.feature_auth.register.presentation.state

sealed interface RegistrationAction {
    data class UserNameAction(val name: String): RegistrationAction
    data class EmailAction(val email: String): RegistrationAction
    data class PasswordAction(val password: String): RegistrationAction
    data class PasswordRepeatAction(val passwordRepeat: String): RegistrationAction
    object TogglePasswordVisibility: RegistrationAction
    object TogglePasswordRepeatVisibility: RegistrationAction
    object Submit: RegistrationAction
    object Cancel: RegistrationAction

}