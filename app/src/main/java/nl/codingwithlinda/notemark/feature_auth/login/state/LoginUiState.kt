package nl.codingwithlinda.notemark.feature_auth.login.state

import nl.codingwithlinda.notemark.core.util.UiText

data class LoginUiState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
)
