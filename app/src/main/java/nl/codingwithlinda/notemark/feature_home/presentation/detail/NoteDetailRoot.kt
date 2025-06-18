package nl.codingwithlinda.notemark.feature_home.presentation.detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.codingwithlinda.notemark.app.NoteMarkApplication
import nl.codingwithlinda.notemark.feature_home.data.local.NoteRepositoryImpl
import nl.codingwithlinda.notemark.feature_home.presentation.detail.components.NoteDetailScreen

@Composable
fun NoteDetailRoot(
    noteId: String
) {
    val localNoteAccess = NoteMarkApplication.localNoteAccess
    val noteRepository = NoteRepositoryImpl(localNoteAccess)
    val viewModel = viewModel<NoteDetailViewModel>(
        factory = viewModelFactory {
            initializer {
                NoteDetailViewModel(
                    noteRepository = noteRepository,
                    noteId = noteId)
            }
        }
    )

    NoteDetailScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        onAction = viewModel::onAction
    )
}