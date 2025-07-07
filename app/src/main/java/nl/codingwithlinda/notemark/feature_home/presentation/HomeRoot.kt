package nl.codingwithlinda.notemark.feature_home.presentation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.navigation.NoteDestination
import nl.codingwithlinda.notemark.core.navigation.SettingsDestination
import nl.codingwithlinda.notemark.core.navigation.nav_intent_handler.NavigationAction
import nl.codingwithlinda.notemark.core.navigation.nav_intent_handler.NavigationIntentHandler
import nl.codingwithlinda.notemark.core.navigation.util.createViewModel
import nl.codingwithlinda.notemark.core.util.ObserveAsEvents
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.detail.NoteDetailRoot
import nl.codingwithlinda.notemark.feature_home.presentation.list.NoteListRoot
import nl.codingwithlinda.notemark.feature_home.presentation.list.NoteListViewModel


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeRoot(
    navigationIntentHandler: NavigationIntentHandler,
    noteRepository: NoteRepository,
    sessionManager: SessionManager
) {
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()

   /* ObserveAsEvents(navigationIntentHandler.handler.receiveAsFlow()) {navAction ->
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
            is NavigationAction.PopupTo -> {}
        }
        println("NAVIGATOR RECEIVED action: $navAction")
    }*/

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = NoteDestination.NoteListDestination
        ) {

            composable<NoteDestination.NoteListDestination> { backstackEntry ->

                val factory = remember() {
                    viewModelFactory {
                        initializer {
                            NoteListViewModel(
                                noteRepository = noteRepository,
                            )
                        }
                    }
                }

                val viewmodel = backstackEntry.createViewModel<NoteListViewModel>(
                    navController = navController,
                    factory = factory
                )
                NoteListRoot(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    viewModel = viewmodel,
                    sessionManager = sessionManager,
                    onEditNote = { noteId ->
                        println("HOME ROOT NAVIGATES TO NOTE DETAIL WITH NOTE DTO $noteId")
                        navController.navigate(NoteDestination.NoteDetailDestination(noteId))
                    },
                    goToSettings = {
                        val intent = NavigationAction.Navigate(
                            SettingsDestination
                        )
                        navigationIntentHandler.sendIntent(intent)
                    }
                )
            }

            composable<NoteDestination.NoteDetailDestination>(

            ) {
                val args = it.toRoute<NoteDestination.NoteDetailDestination>().noteId
                println("HOME ROOT NAVIGATED TO NOTE DETAIL. NOTE ID RECEIVED: $args")

                NoteDetailRoot(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    noteRepository = noteRepository,
                    noteId = args,
                    navBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
  /*  NavDisplay(
        backStack = backstack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = { route ->

            when (route) {
                is NoteDestination.NoteListDestination -> {
                    NavEntry(route) {
                        NoteListRoot(
                            sessionManager = sessionManager,
                            noteRepository = noteRepository,
                            onEditNote = {
                                backstack.retainAll(listOf(NoteDestination.NoteListDestination))
                                backstack.add(NoteDestination.NoteDetailDestination(it))
                                println("HOME ROOT NAVIGATES TO NOTE DETAIL. BACKSTACK: $backstack")
                            },
                            onOffset = {
                                val transformX = it.x / currentWidth.toFloat()
                                val transformY = (it.y) / currentHeight.toFloat()
                                //println("CURRENT WIDTH: $currentWidth, CURRENT HEIGHT: $currentHeight")
                                println("HOME ROOT SETS OFFSET: $it, TRANSFORM: $transformX, $transformY")
                                transformOrigin = TransformOrigin(transformX, transformY)
                            }
                        )
                    }
                }

                is NoteDestination.NoteDetailDestination -> {
                    val noteDto = route.noteDto
                    NavEntry(
                        route,
                        metadata = NavDisplay.transitionSpec {
                           scaleIn(
                                animationSpec = tween(500),
                                transformOrigin = transformOrigin
                            ) + fadeIn(
                                animationSpec = tween(500)
                            )  togetherWith scaleOut(
                                animationSpec = tween(2000),
                                targetScale = 0.01f,
                                transformOrigin = transformOrigin
                            ) + fadeOut(
                                animationSpec = tween(1000)
                            )
//
                        } + NavDisplay.popTransitionSpec {
                            EnterTransition.None togetherWith scaleOut(
                                animationSpec = tween(500),
                                targetScale = 0.1f,
                                transformOrigin = transformOrigin
                            )
//
                        } + NavDisplay.predictivePopTransitionSpec {
                            // Slide old content down, revealing the new content in place underneath
                            EnterTransition.None togetherWith
                                    slideOutVertically(
                                        targetOffsetY = { it },
                                        animationSpec = tween(1000)
                                    )
                        }
                    ) {
                        NoteDetailRoot(
                            noteRepository = noteRepository,
                            noteDto = noteDto,
                            navBack = {
                                backstack.retainAll(listOf(NoteDestination.NoteListDestination))
                            }
                        )
                    }
                }

                else -> {
                    throw RuntimeException("Unknown route")
                }
            }
        },
        transitionSpec = {
            // Slide in from right when navigating forward
            slideInHorizontally(
                animationSpec = tween(1000),
                initialOffsetX = { it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { -it })
        },
        popTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(
                animationSpec = tween(1000),
                initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
        predictivePopTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
    )
*/
}

