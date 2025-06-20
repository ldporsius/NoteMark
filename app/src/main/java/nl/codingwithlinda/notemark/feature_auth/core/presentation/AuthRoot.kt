package nl.codingwithlinda.notemark.feature_auth.core.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.app.NoteMarkApplication
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.navigation.AuthDestination
import nl.codingwithlinda.notemark.core.util.ObserveAsEvents
import nl.codingwithlinda.notemark.design_system.ui.theme.primary
import nl.codingwithlinda.notemark.design_system.ui.theme.surfaceLowest
import nl.codingwithlinda.notemark.core.util.SnackBarController
import nl.codingwithlinda.notemark.feature_auth.login.presentation.LoginRoot
import nl.codingwithlinda.notemark.feature_auth.register.presentation.RegisterRoot
import nl.codingwithlinda.notemark.feature_auth.landing.LandingScreen

@Composable
fun AuthRoot(
    sessionManager: SessionManager,
    navigateHome: () -> Unit,
    modifier: Modifier = Modifier) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    ObserveAsEvents(SnackBarController.events, snackbarHostState) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val result = snackbarHostState.showSnackbar(
                message = event.message.asString(context),
                actionLabel = event.action?.name?.asString(context),
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.ActionPerformed){
                event.action?.action?.invoke()
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = primary,
        snackbarHost = {
            androidx.compose.material3.SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->

        Surface(
            color = surfaceLowest,
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
                           sessionManager = sessionManager,
                           navToRegister = {
                               println("LOGIN ROOT IS NAVIGATING TO REGISTER")
                               //printBackStack(backstackAuth)
                               backstackAuth.clear()
                               backstackAuth.add(AuthDestination.WelcomeDestination)
                               backstackAuth.add(AuthDestination.RegisterDestination)
                           },
                           onLoginSuccess = {
                               println("LOGIN ROOT IS NAVIGATING TO HOME")
                               backstackAuth.retainAll(listOf(AuthDestination.WelcomeDestination))
                               printBackStack(backstackAuth)
                               navigateHome()
                           }
                       )
                    }

                    entry(AuthDestination.RegisterDestination) {
                        RegisterRoot(
                            registerService = NoteMarkApplication.appModule.registerService,
                            navToHome = {
                                navigateHome()
                            },
                            navToLogin = {
                                println("REGISTER ROOT Backstack before NAVIGATING TO LOGIN")
                                printBackStack(backstackAuth)
                                backstackAuth.retainAll(listOf(AuthDestination.WelcomeDestination))
                                backstackAuth.add(AuthDestination.LoginDestination)
                                println("REGISTER ROOT Backstack after NAVIGATING TO LOGIN")
                                printBackStack(backstackAuth)
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

