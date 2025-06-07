package nl.codingwithlinda.notemark.feature_auth.login.presentation.state

import nl.codingwithlinda.notemark.core.util.UiText

data class LoginUiState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordVisible: Boolean = false,
    val passwordError: UiText? = null,
    val isLoading: Boolean = false
){
    fun isLoginEnabled(): Boolean {
        val isInput = email.isNotBlank() && password.isNotBlank()
        val isNotError = emailError == null && passwordError == null
        return isInput && isNotError
    }
}
