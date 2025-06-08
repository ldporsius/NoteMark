package nl.codingwithlinda.notemark.feature_auth.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.codingwithlinda.notemark.core.data.auth.login.LoginService
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenTwoComposablesConstraint
import nl.codingwithlinda.notemark.design_system.ui.theme.surfaceLowest
import nl.codingwithlinda.notemark.feature_auth.login.presentation.components.LoginForm
import nl.codingwithlinda.notemark.feature_auth.login.presentation.components.LoginHeader

@Composable
fun LoginRoot(
    loginService: LoginService,
    navToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {

    val loginViewModel = viewModel<LoginViewModel>(
        factory = viewModelFactory {
            initializer {
                LoginViewModel(
                    loginService = loginService,
                    navToRegister = navToRegister,
                    onLoginSuccess = onLoginSuccess
                )
            }
        }
    )


    ScreenTwoComposablesConstraint(
        comp1 = {
            LoginHeader()
        },
        comp2 = {
            LoginForm(
                uiState = loginViewModel.uiState.collectAsStateWithLifecycle().value,
                onAction = loginViewModel::handleAction,
                modifier = Modifier
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                ,
            )
        },
        modifier = Modifier

            .fillMaxSize()
            .background(color = surfaceLowest)
            .padding(16.dp)

    )
}