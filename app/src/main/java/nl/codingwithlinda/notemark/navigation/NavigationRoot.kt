package nl.codingwithlinda.notemark.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import nl.codingwithlinda.notemark.core.navigation.AuthRootDestination
import nl.codingwithlinda.notemark.core.navigation.HomeDestination
import nl.codingwithlinda.notemark.feature_auth.core.presentation.AuthRoot
import nl.codingwithlinda.notemark.feature_home.presentation.HomeRoot

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backstack = rememberNavBackStack(
        AuthRootDestination
    )

    NavDisplay(
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
                    HomeRoot()
                }
            }
            is AuthRootDestination -> {

                NavEntry(route) {
                    AuthRoot(
                        navigateBack = {
                            backstack.retainAll(
                                listOf(AuthRootDestination)
                            )
                        },
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
    )
}