package nl.codingwithlinda.notemark.feature_auth.register.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.codingwithlinda.notemark.BuildConfig
import nl.codingwithlinda.notemark.app.NoteMarkApplication
import nl.codingwithlinda.notemark.core.data.auth.register.RegisterService
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenTwoComposablesConstraint
import nl.codingwithlinda.notemark.design_system.ui.theme.surfaceLowest
import nl.codingwithlinda.notemark.feature_auth.register.presentation.components.RegistrationForm
import nl.codingwithlinda.notemark.feature_auth.register.presentation.components.RegistrationHeader

@Composable
fun RegisterRoot(
    navToLogin: () -> Unit,
    navToHome: () -> Unit
) {

    val viewModel = viewModel< RegistrationViewModel>(
        factory = viewModelFactory {
            initializer {
                RegistrationViewModel(
                    registerService = NoteMarkApplication.registerService,
                    onSuccess = {
                        navToHome()
                    },
                    onCancel = navToLogin
                )
            }
        }
    )

    Surface(
        color = surfaceLowest,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScreenTwoComposablesConstraint(
            comp1 = {
                RegistrationHeader()
            },
            comp2 = {
                RegistrationForm(
                    uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                    onAction = viewModel::handleAction,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .imePadding()
                        .padding(16.dp)
                )
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}