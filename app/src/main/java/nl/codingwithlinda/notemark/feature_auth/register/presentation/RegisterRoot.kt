package nl.codingwithlinda.notemark.feature_auth.register.presentation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.BuildConfig
import nl.codingwithlinda.notemark.core.data.auth.register.RegisterService
import nl.codingwithlinda.notemark.core.util.ObserveAsEvents
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenTwoComposables
import nl.codingwithlinda.notemark.design_system.ui.theme.surfaceLowest
import nl.codingwithlinda.notemark.design_system.util.SnackBarController
import nl.codingwithlinda.notemark.design_system.util.SnackbarEvent
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
                    registerService = RegisterService.create(
                        authorizer = BuildConfig.AUTH_API_EMAIL
                    ),
                    onSuccess = {
                        navToHome()
                    },
                    onCancel = navToLogin
                )
            }
        }
    )

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    ObserveAsEvents(SnackBarController.events, snackbarHostState) {event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val result = snackbarHostState.showSnackbar(
                message = event.message.asString(context),
                actionLabel = event.action?.name?.asString(context),
                duration = SnackbarDuration.Long

            )
           if (result == SnackbarResult.ActionPerformed){
               event.action?.action?.invoke()
           }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = surfaceLowest,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        Surface(
            color = surfaceLowest,
            modifier = Modifier
                .consumeWindowInsets(innerPadding)
                .padding(16.dp)
        ) {
            ScreenTwoComposables(
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
}