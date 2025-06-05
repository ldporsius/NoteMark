package nl.codingwithlinda.notemark.feature_auth.register.presentation.state

import nl.codingwithlinda.notemark.core.util.UiText

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
)
