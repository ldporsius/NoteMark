package nl.codingwithlinda.notemark.feature_auth.login.presentation.state

import nl.codingwithlinda.notemark.core.util.UiText

data class LoginUiState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordVisible: Boolean = false,
    val passwordError: UiText? = null,
)
