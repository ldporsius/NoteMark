package nl.codingwithlinda.notemark.feature_auth.login.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.notemark.feature_auth.login.presentation.state.LoginAction
import nl.codingwithlinda.notemark.feature_auth.login.presentation.state.LoginUiState

class LoginViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun handleAction(action: LoginAction){
        when(action){
            is LoginAction.EmailChanged -> {
                _uiState.update {
                    it.copy(
                        email = action.email
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
            LoginAction.Submit -> {
                //todo validate email and password
            }
            LoginAction.TogglePasswordVisibility -> {
                _uiState.update {
                    it.copy(
                        passwordVisible = !it.passwordVisible
                    )
                }
            }
        }
    }
}