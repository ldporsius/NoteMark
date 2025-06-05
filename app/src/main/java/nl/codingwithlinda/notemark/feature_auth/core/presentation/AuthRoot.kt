package nl.codingwithlinda.notemark.feature_auth.core.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import nl.codingwithlinda.notemark.core.navigation.AuthDestination
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.primary
import nl.codingwithlinda.notemark.design_system.ui.theme.surface
import nl.codingwithlinda.notemark.feature_auth.login.presentation.LoginRoot
import nl.codingwithlinda.notemark.feature_auth.register.presentation.RegisterRoot
import nl.codingwithlinda.notemark.feature_home.LandingScreen

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
                AuthDestination.WelcomeDestination
            )

            NavDisplay(
                backStack = backstackAuth,
                entryProvider = entryProvider {
                    entry(AuthDestination.WelcomeDestination) {
                        LandingScreen(
                            onGetStartedClick = {
                                backstackAuth.add(AuthDestination.RegisterDestination)
                            },
                            onLoginClick = {
                                backstackAuth.add(AuthDestination.LoginDestination)
                            },
                            modifier = Modifier
                        )
                    }

                    entry(AuthDestination.LoginDestination) {
                       LoginRoot(
                           navToRegister = {
                               backstackAuth.remove(AuthDestination.LoginDestination)
                               backstackAuth.add(AuthDestination.RegisterDestination)
                           }
                       )
                    }

                    entry(AuthDestination.RegisterDestination) {
                        RegisterRoot(
                            navToLogin = {
                                backstackAuth.remove(AuthDestination.RegisterDestination)
                                backstackAuth.add(AuthDestination.LoginDestination)
                            }
                        )
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