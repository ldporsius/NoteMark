package nl.codingwithlinda.notemark.feature_home.presentation.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.navigation.dto.EditNoteDto
import nl.codingwithlinda.notemark.core.util.ObserveAsEvents
import nl.codingwithlinda.notemark.core.util.SnackBarController
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.detail.components.NoteDetailScreen

@Composable
fun NoteDetailRoot(
    noteId: String,
    noteRepository: NoteRepository,
    navBack: () -> Unit,
) {

    LaunchedEffect(Unit) {
        Log.d("MyScreenLifecycle", "NoteDetailScreen Composable Launched")
    }
    DisposableEffect(Unit) {
        onDispose {
            Log.d("MyScreenLifecycle", "NoteDetailScreen Composable Disposed")
        }
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    /*ObserveAsEvents(SnackBarController.events){ event ->
        scope.launch {
            event.action?.action?.invoke()
            event.message.asString(context).let { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }*/
    val viewModel = viewModel<NoteDetailViewModel>(
        factory = viewModelFactory {
            initializer {
                NoteDetailViewModel(
                    noteRepository = noteRepository,
                    noteId = noteId,
                    navBack = navBack
                )
            }
        }
    )

    NoteDetailScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        onAction = viewModel::onAction
    )
}