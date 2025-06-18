package nl.codingwithlinda.notemark.feature_home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.navigation.NoteDestination
import nl.codingwithlinda.notemark.design_system.ui.theme.surfaceLowest
import nl.codingwithlinda.notemark.feature_home.presentation.list.NoteListRoot

@Composable
fun HomeRoot(
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
                            onEditNote = {
                                backstack.retainAll(listOf(NoteDestination.NoteListDestination))
                                backstack.add(NoteDestination.NoteDetailDestination(it))
                            })
                    }
                }
                is NoteDestination.NoteDetailDestination -> {
                    val noteId = route.noteId
                    NavEntry(route) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(surfaceLowest)
                                .safeDrawingPadding()
                        ){
                            Text(text = "Note detail $noteId")
                        }
                    }
                }
                else -> {
                    throw RuntimeException("Unknown route")
                }
            }
        }
    )
}

