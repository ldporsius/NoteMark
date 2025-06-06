package nl.codingwithlinda.notemark.feature_auth.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.data.auth.login.LoginService
import nl.codingwithlinda.notemark.core.domain.auth.AuthError
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_auth.login.domain.LoginValidator
import nl.codingwithlinda.notemark.feature_auth.login.presentation.error_ui.toUi
import nl.codingwithlinda.notemark.feature_auth.login.presentation.state.LoginAction
import nl.codingwithlinda.notemark.feature_auth.login.presentation.state.LoginUiState

class LoginViewModel(
    private val loginService: LoginService,
    private val navToRegister: () -> Unit,
    private val onLoginSuccess: () -> Unit
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _errorChannel = Channel<AuthError>()
    val errorChannel = _errorChannel.receiveAsFlow()

    fun handleAction(action: LoginAction){
        when(action){
            is LoginAction.EmailChanged -> {
                val isEmailValid = LoginValidator.validateLoginEmail(action.email)?.let {
                    it.toUi()
                }
                _uiState.update {
                    it.copy(
                        email = action.email,
                        emailError = isEmailValid
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
                    loginService.login(
                        email = _uiState.value.email,
                        password = _uiState.value.password
                    ).also {res ->
                        when(res){
                            is Result.Error -> {
                                _errorChannel.send(res.error)
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