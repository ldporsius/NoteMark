package nl.codingwithlinda.notemark.feature_auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.window.core.layout.WindowWidthSizeClass
import nl.codingwithlinda.notemark.core.navigation.AuthDestination
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenTwoComposables
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.background_landing
import nl.codingwithlinda.notemark.design_system.ui.theme.primary
import nl.codingwithlinda.notemark.design_system.ui.theme.surface
import nl.codingwithlinda.notemark.design_system.ui.theme.surfaceLowest
import nl.codingwithlinda.notemark.feature_auth.login.presentation.LoginRoot
import nl.codingwithlinda.notemark.feature_auth.login.presentation.components.LoginForm
import nl.codingwithlinda.notemark.feature_auth.login.presentation.components.LoginHeader
import nl.codingwithlinda.notemark.feature_auth.login.presentation.state.LoginUiState
import nl.codingwithlinda.notemark.feature_auth.register.presentation.RegisterRoot

@Composable
fun AuthRoot(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = primary
    ) { innerPadding ->

        Surface(
            color = surface,
            modifier = modifier
                .padding(innerPadding)
                .padding(top = 16.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {

            val backstackAuth: NavBackStack = rememberNavBackStack<AuthDestination>(
                AuthDestination.LoginDestination
            )

            NavDisplay(
                backStack = backstackAuth,
                entryProvider = entryProvider {
                    entry(AuthDestination.LoginDestination) {
                       LoginRoot()
                    }

                    entry(AuthDestination.RegisterDestination) {
                        RegisterRoot()
                    }
                }
            )
        }
    }
}

@Preview(apiLevel = 35)
@Composable
private fun AuthRootPreview() {
    NoteMarkTheme {
        AuthRoot(
            onLoginSuccess = {}
        )

    }
}