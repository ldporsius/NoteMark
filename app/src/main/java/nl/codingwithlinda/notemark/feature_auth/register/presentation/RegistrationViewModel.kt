package nl.codingwithlinda.notemark.feature_auth.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.data.auth.register.RegisterRequestDto
import nl.codingwithlinda.notemark.core.data.auth.register.RegisterService
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.core.util.UiText
import nl.codingwithlinda.notemark.design_system.util.SnackBarController
import nl.codingwithlinda.notemark.design_system.util.SnackbarEvent
import nl.codingwithlinda.notemark.feature_auth.core.presentation.error_mapping.toUiText
import nl.codingwithlinda.notemark.feature_auth.register.domain.RegistrationValidator
import nl.codingwithlinda.notemark.feature_auth.register.presentation.error_mapping.toUiText
import nl.codingwithlinda.notemark.feature_auth.register.presentation.state.RegistrationAction
import nl.codingwithlinda.notemark.feature_auth.register.presentation.state.RegistrationUiState

class RegistrationViewModel(
    private val registerService: RegisterService,
    private val onSuccess: () -> Unit,
    private val onCancel: () -> Unit,
): ViewModel() {
    private val validator =
        RegistrationValidator

    private val _errorChannel = Channel<UiText>()
    val errorChannel = _errorChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(RegistrationUiState())

    val uiState = _uiState.map {newState->
        val allInputSet = setOf(
            newState.username,
            newState.email,
            newState.password,
            newState.passwordRepeat
        ).all { it.isNotBlank() }
        val errorSet = setOf(
            newState.usernameError,
            newState.emailError,
            newState.passwordError,
            newState.passwordRepeatError
        ).filterNotNull()

        newState.copy(
            isRegistrationEnabled = errorSet.isEmpty() && allInputSet
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    fun handleAction(action: RegistrationAction) = viewModelScope.launch {
        when (action) {
            RegistrationAction.Cancel -> {
                onCancel()
            }
            is RegistrationAction.UserNameAction -> {
                    _uiState.update {
                        it.copy(
                            username = action.name,
                        )
                    }
            }
            is RegistrationAction.EmailAction -> {
                    _uiState.update { it.copy(
                        email = action.email,
                    )
                }
            }
            is RegistrationAction.PasswordAction -> {
                    _uiState.update { it.copy(
                        password = action.password,
                    )
                }
            }
            is RegistrationAction.PasswordRepeatAction -> {
                    _uiState.update {
                        it.copy(
                            passwordRepeat = action.passwordRepeat,
                        )
                    }
            }
            RegistrationAction.Submit -> {
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                registerService.register(
                    RegisterRequestDto(
                        username = _uiState.value.username,
                        email = _uiState.value.email,
                        password = _uiState.value.password,
                    )
                ).also {res ->
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }

                    when(res){
                        is Result.Error -> {
                            println("Registration has error: ${res.error}")
                            SnackBarController.sendEvent(
                                SnackbarEvent(
                                    message = res.error.toUiText(),
                                )
                            )
                        }
                        is Result.Success -> {
                            onSuccess()
                        }
                    }
                }
            }
            RegistrationAction.TogglePasswordVisibility -> {
                _uiState.value = _uiState.value.copy(
                    passwordVisible = !_uiState.value.passwordVisible
                )
            }
            RegistrationAction.TogglePasswordRepeatVisibility -> {
                _uiState.value = _uiState.value.copy(
                    passwordRepeatVisible = !_uiState.value.passwordRepeatVisible
                )
            }
        }

        validateAllInputs()

    }

    private fun validateAllInputs() {
        clearErrors()
        val currentState = _uiState.value
        validator.validateUsername(currentState.username).also {error ->
            _uiState.update {
                it.copy(
                    usernameError = error?.toUiText()
                )
            }
        }
        validator.validateEmail(currentState.email).also {error ->
            _uiState.update {
                it.copy(
                    emailError = error?.toUiText()
                )
            }
        }

        validator.validatePassword(currentState.password).also {error->
            _uiState.update {
                it.copy(
                    passwordError = error?.toUiText()
                )
            }
        }
        validator.validateRepeatedPassword(
            currentState.password,
            currentState.passwordRepeat
        ).also {error->
            _uiState.update {
                it.copy(
                    passwordRepeatError = error?.toUiText()
                )
            }
        }
    }

    private fun clearErrors(){
        _uiState.update {
            it.copy(
                usernameError = null,
                emailError = null,
                passwordError = null,
                passwordRepeatError = null
            )
        }
    }
}