package nl.codingwithlinda.notemark.feature_auth.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.data.auth.login.LoginRequestDto
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.core.util.SnackBarController
import nl.codingwithlinda.notemark.core.util.SnackbarEvent
import nl.codingwithlinda.notemark.feature_auth.core.presentation.error_mapping.toUiText
import nl.codingwithlinda.notemark.feature_auth.login.presentation.state.LoginAction
import nl.codingwithlinda.notemark.feature_auth.login.presentation.state.LoginUiState

class LoginViewModel(
    private val sessionManager: SessionManager,
    private val navToRegister: () -> Unit,
    private val onLoginSuccess: () -> Unit
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun handleAction(action: LoginAction){
        when(action){
            is LoginAction.EmailChanged -> {
                _uiState.update {
                    it.copy(
                        email = action.email,
                    )
                }
            }
            is LoginAction.PasswordChanged -> {
                _uiState.update {
                    it.copy(
                        password = action.password
                    )
                }
            }

            LoginAction.TogglePasswordVisibility -> {
                _uiState.update {
                    it.copy(
                        passwordVisible = !it.passwordVisible
                    )
                }
            }

            LoginAction.Submit -> {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    sessionManager.login(
                        LoginRequestDto(
                            email = _uiState.value.email,
                            password = _uiState.value.password
                        )
                    ).also {res ->
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        when(res){
                            is Result.Error -> {
                                SnackBarController.sendEvent(
                                    SnackbarEvent(
                                        message = res.error.toUiText(),
                                    )
                                )
                            }
                            is Result.Success -> {
                                onLoginSuccess()
                            }
                        }
                    }
                }
            }

            LoginAction.Cancel -> {
                navToRegister()
            }

        }
    }
}