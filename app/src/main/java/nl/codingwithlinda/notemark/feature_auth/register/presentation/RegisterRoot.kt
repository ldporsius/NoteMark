package nl.codingwithlinda.notemark.feature_auth.register.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenTwoComposables
import nl.codingwithlinda.notemark.feature_auth.register.presentation.components.RegistrationForm
import nl.codingwithlinda.notemark.feature_auth.register.presentation.components.RegistrationHeader
import nl.codingwithlinda.notemark.feature_auth.register.presentation.state.RegistrationAction
import nl.codingwithlinda.notemark.feature_auth.register.presentation.state.RegistrationUiState

@Composable
fun RegisterRoot(
    navToLogin: () -> Unit,
) {
    Scaffold { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)

        ) {
            ScreenTwoComposables(
                comp1 = {
                    RegistrationHeader()
                },
                comp2 = {
                    RegistrationForm(
                        uiState = RegistrationUiState(),
                        onAction = {
                            if (it is RegistrationAction.Cancel) {
                                navToLogin()
                            }
                        }
                    )
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}