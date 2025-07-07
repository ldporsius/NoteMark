package nl.codingwithlinda.notemark.feature_home.presentation.detail

import android.widget.Toast
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.util.ObserveAsEvents
import nl.codingwithlinda.notemark.core.util.SnackBarController
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.detail.components.edit.NoteDetailScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NoteDetailRoot(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    noteId: String,
    noteRepository: NoteRepository,
    navBack: () -> Unit,
) {
    val viewModel = viewModel<NoteDetailViewModel>(
        factory = viewModelFactory {
            initializer {
                NoteDetailViewModel(
                    noteRepository = noteRepository,
                    noteId = noteId,
                )
            }
        }
    )
    ObserveAsEvents(viewModel.navBackChannel.receiveAsFlow()) {
        navBack()
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    ObserveAsEvents(SnackBarController.events){ event ->
        scope.launch {
            event.action?.action?.invoke()
            event.message.asString(context).let { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }


    with(sharedTransitionScope) {
        NoteDetailScreen(
            uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
            onAction = viewModel::onAction,
            modifier = Modifier.sharedElement(
                sharedTransitionScope.rememberSharedContentState(key = noteId),
                animatedVisibilityScope = animatedContentScope
            )

        )
    }
}