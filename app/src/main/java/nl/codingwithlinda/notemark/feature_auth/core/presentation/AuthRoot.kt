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
import nl.codingwithlinda.notemark.feature_auth.landing.LandingScreen

@Composable
fun AuthRoot(
    navigateBack: () -> Unit,
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
                                println("LANDING SCREEN IS NAVIGATING TO REGISTER")
                                printBackStack(backstackAuth)
                                backstackAuth.add( AuthDestination.RegisterDestination)
                            },
                            onLoginClick = {
                                println("LANDING SCREEN IS NAVIGATING TO LOGIN")
                                printBackStack(backstackAuth)
                                backstackAuth.add( AuthDestination.LoginDestination)
                            },
                            modifier = Modifier
                        )
                    }

                    entry(AuthDestination.LoginDestination) {
                       LoginRoot(
                           navToRegister = {
                               println("LOGIN ROOT IS NAVIGATING TO REGISTER")
                               printBackStack(backstackAuth)
                               backstackAuth.clear()
                               backstackAuth.add(AuthDestination.WelcomeDestination)
                               backstackAuth.add(AuthDestination.RegisterDestination)
                           }
                       )
                    }

                    entry(AuthDestination.RegisterDestination) {
                        RegisterRoot(
                            navToHome = {
                                backstackAuth.clear()
                                navigateBack()
                            },
                            navToLogin = {
                                println("REGISTER ROOT IS NAVIGATING TO LOGIN")
                                printBackStack(backstackAuth)
                                backstackAuth.clear()
                                backstackAuth.add(AuthDestination.WelcomeDestination)
                                backstackAuth.add(AuthDestination.LoginDestination)
                            }
                        )
                    }
                }
            )
        }
    }
}

fun printBackStack(backStack: NavBackStack) {
    println("************** printing backstack ***************")
    backStack.onEachIndexed { index, navKey ->
        println("BACKSTACK ENTRY: $index -> $navKey")
    }
}

@Preview(apiLevel = 35)
@Composable
private fun AuthRootPreview() {
    NoteMarkTheme {
        AuthRoot(
            navigateBack = {}
        )

    }
}