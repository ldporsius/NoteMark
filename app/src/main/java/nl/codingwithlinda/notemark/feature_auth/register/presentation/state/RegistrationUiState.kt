package nl.codingwithlinda.notemark.feature_auth.register.presentation.state

import nl.codingwithlinda.notemark.R
import nl.codingwithlinda.notemark.core.util.UiText
import nl.codingwithlinda.notemark.feature_auth.register.domain.RegistrationValidator.MAX_USERNAME_LENGTH
import nl.codingwithlinda.notemark.feature_auth.register.domain.RegistrationValidator.MIN_USERNAME_LENGTH

data class RegistrationUiState(
    val username: String = "",
    val usernameError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val passwordVisible: Boolean = false,
    val passwordRepeat: String = "",
    val passwordRepeatError: UiText? = null,
    val passwordRepeatVisible: Boolean = false,
    val isRegistrationEnabled: Boolean = false
){
    fun userNameSupportingText(): UiText {
        if (usernameError == null){
            return UiText.StringResourceText(R.string.username_hint, listOf( MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH))
        }
        return usernameError
    }
}
