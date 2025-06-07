package nl.codingwithlinda.notemark.feature_auth.register.presentation.error_mapping

import nl.codingwithlinda.notemark.core.util.UiText
import nl.codingwithlinda.notemark.feature_auth.register.domain.RegistrationEmailError
import nl.codingwithlinda.notemark.feature_auth.register.domain.RegistrationError
import nl.codingwithlinda.notemark.feature_auth.register.domain.RegistrationPasswordError
import nl.codingwithlinda.notemark.feature_auth.register.domain.RegistrationUserNameError

fun RegistrationError.toUiText(): UiText {
    return when (this) {
        is RegistrationUserNameError -> this.toUiText()
        is RegistrationEmailError -> this.toUiText()
        is RegistrationPasswordError -> this.toUiText()

        else -> {UiText.DynamicText("Unknown error")}
    }
}
fun RegistrationUserNameError.toUiText(): UiText{
    return when(this){
        RegistrationUserNameError.EmptyUsername -> UiText.DynamicText("Username cannot be empty")
        RegistrationUserNameError.UsernameShort -> UiText.DynamicText("Username must be at least 3 characters long")
        RegistrationUserNameError.UsernameLong -> UiText.DynamicText("Username must be at most 20 characters long")}
}
fun RegistrationEmailError.toUiText(): UiText{
    return when(this){
        RegistrationEmailError.EmptyEmail -> UiText.DynamicText("Email cannot be empty")
        RegistrationEmailError.InvalidEmail -> UiText.DynamicText("Invalid email")
    }
}
fun RegistrationPasswordError.toUiText(): UiText{
    return when(this){
        RegistrationPasswordError.EmptyPassword -> UiText.DynamicText("Password cannot be empty")
        RegistrationPasswordError.PasswordShort -> UiText.DynamicText("Password must be at least 8 characters long")
        RegistrationPasswordError.PasswordInvalid -> UiText.DynamicText("Password must contain at least 1 special character or 1 number")
        RegistrationPasswordError.PasswordsNotMatching -> UiText.DynamicText("Passwords do not match")
    }
}