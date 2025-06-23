package nl.codingwithlinda.notemark.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.navigation.AuthRootDestination
import nl.codingwithlinda.notemark.core.navigation.HomeDestination
import nl.codingwithlinda.notemark.core.navigation.NoteDestination
import nl.codingwithlinda.notemark.core.util.ObserveAsEvents
import nl.codingwithlinda.notemark.feature_auth.core.presentation.AuthRoot
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.HomeRoot

@Composable
fun NavigationRoot(
    sessionManager: SessionManager,
    noteRepository: NoteRepository
) {

    val backstack = rememberNavBackStack(
        HomeDestination
    )

    ObserveAsEvents(sessionManager.loginState) { session ->
        if (session.accessToken.isBlank()){
            backstack.add(AuthRootDestination)
        }
    }

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeDestination) {

        composable<HomeDestination> {
            HomeRoot(
                sessionManager = sessionManager,
                noteRepository = noteRepository
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