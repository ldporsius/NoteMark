package nl.codingwithlinda.notemark.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation3.runtime.rememberNavBackStack
import kotlinx.coroutines.flow.receiveAsFlow
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.navigation.nav_intent_handler.NavigationAction
import nl.codingwithlinda.notemark.core.navigation.nav_intent_handler.NavigationIntentHandler
import nl.codingwithlinda.notemark.core.util.ObserveAsEvents
import nl.codingwithlinda.notemark.feature_auth.core.presentation.AuthRoot
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.HomeRoot
import nl.codingwithlinda.notemark.feature_settings.presentation.SettingsRoot
import nl.codingwithlinda.notemark.feature_settings.presentation.SettingsViewModel

@Composable
fun MainNavGraph(
    navigationIntentHandler: NavigationIntentHandler,
    sessionManager: SessionManager,
    noteRepository: NoteRepository
) {

    val backstack = rememberNavBackStack(
        HomeDestination
    )
    val navController = rememberNavController()
    ObserveAsEvents(sessionManager.loginState) { session ->
        if (session.accessToken.isBlank()){
            //backstack.add(AuthRootDestination)
            navController.navigate(AuthRootDestination){
                popUpTo(HomeDestination){
                    inclusive = true
                }

            }
        }
    }

    ObserveAsEvents(navigationIntentHandler.handler.receiveAsFlow()) {navAction ->
        when(navAction){
            is NavigationAction.Navigate -> {
                navController.navigate(navAction.destination){
                    navAction.navOptions
                }
            }
            NavigationAction.NavigateUp -> {
                navController.navigateUp()
            }
            NavigationAction.PopBackStack -> {
                navController.popBackStack()
            }
            is NavigationAction.PopupTo -> {
                navController.navigate(navAction.destination){
                    popUpTo(navAction.destination){
                        inclusive = true
                    }
                }
            }
        }

    }


    NavHost(navController = navController, startDestination = HomeDestination) {

        composable<HomeDestination> {
            HomeRoot(
                navigationIntentHandler = navigationIntentHandler,
                sessionManager = sessionManager,
                noteRepository = noteRepository
            )
        }
        composable<SettingsDestination> {
            val settingsViewModel = viewModel<SettingsViewModel>(
                factory = viewModelFactory {
                    initializer {
                        SettingsViewModel(
                            noteRepo = noteRepository,
                            sessionManager = sessionManager,
                            navigator = navigationIntentHandler
                        )
                    }
                }
            )

            SettingsRoot(
                viewModel = settingsViewModel,
                onBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<AuthRootDestination> {
            AuthRoot(
                sessionManager = sessionManager,
                navigateHome = {
                   navController.navigate(HomeDestination)
                },
                modifier = Modifier
            )
        }
    }
   /* NavDisplay(
        backStack = backstack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = { route ->
        when(route) {
            is HomeDestination -> {
                NavEntry(route) {
                    HomeRoot(
                        sessionManager = sessionManager,
                        noteRepository = noteRepository
                    )
                }
            }
            is AuthRootDestination -> {

                NavEntry(route) {
                    AuthRoot(
                        sessionManager = sessionManager,
                        navigateHome = {
                            backstack.retainAll(
                                listOf(AuthRootDestination)
                            )
                            backstack.add(HomeDestination)
                        },
                        modifier = Modifier
                    )
                }
            }

            else -> {
               throw RuntimeException("Unknown route")
            }
        }
    }
    )*/
}