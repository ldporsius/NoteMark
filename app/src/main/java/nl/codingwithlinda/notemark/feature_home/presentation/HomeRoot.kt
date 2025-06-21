package nl.codingwithlinda.notemark.feature_home.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.navigation.NoteDestination
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.detail.NoteDetailRoot
import nl.codingwithlinda.notemark.feature_home.presentation.list.NoteListRoot

@Composable
fun HomeRoot(
    noteRepository: NoteRepository,
    sessionManager: SessionManager
) {

    val backstack = rememberNavBackStack(
        NoteDestination.NoteListDestination
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
                is NoteDestination.NoteListDestination -> {
                    NavEntry(route) {
                        NoteListRoot(
                            sessionManager = sessionManager,
                            noteRepository = noteRepository,
                            onEditNote = {
                                backstack.retainAll(listOf(NoteDestination.NoteListDestination))
                                backstack.add(NoteDestination.NoteDetailDestination(it))
                                println("HOME ROOT NAVIGATES TO NOTE DETAIL. BACKSTACK: $backstack")
                            })
                    }
                }
                is NoteDestination.NoteDetailDestination -> {
                    val noteDto = route.noteDto
                    NavEntry(route) {
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
}

