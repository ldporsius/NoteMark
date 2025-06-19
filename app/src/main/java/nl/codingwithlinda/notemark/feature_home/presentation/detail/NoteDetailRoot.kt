package nl.codingwithlinda.notemark.feature_home.presentation.detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.codingwithlinda.notemark.core.navigation.dto.EditNoteDto
import nl.codingwithlinda.notemark.core.util.ObserveAsEvents
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.detail.components.NoteDetailScreen

@Composable
fun NoteDetailRoot(
    noteDto: EditNoteDto,
    noteRepository: NoteRepository,
    navBack: () -> Unit
) {

    val viewModel = viewModel<NoteDetailViewModel>(
        factory = viewModelFactory {
            initializer {
                NoteDetailViewModel(
                    noteRepository = noteRepository,
                    noteDto = noteDto,
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