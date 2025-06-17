package nl.codingwithlinda.notemark.feature_home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.codingwithlinda.notemark.design_system.ui.theme.backgroundGradient
import nl.codingwithlinda.notemark.design_system.ui.theme.onPrimary
import nl.codingwithlinda.notemark.design_system.ui.theme.primary
import nl.codingwithlinda.notemark.feature_home.data.local.NoteRepositoryImpl
import nl.codingwithlinda.notemark.feature_home.presentation.list.NoteListViewModel
import nl.codingwithlinda.notemark.feature_home.presentation.list.components.NoteListScreen

@Composable
fun HomeRoot() {
    val repo = NoteRepositoryImpl()
    val notesViewModel = viewModel<NoteListViewModel>(
        factory = viewModelFactory {
            initializer {
                NoteListViewModel(repo)
            }
        }
    )
    Scaffold(
        containerColor = primary,
        contentColor = onPrimary
    ) {innerPadding ->
        Box(Modifier
            .fillMaxSize()
            .background(brush = backgroundGradient)
            .padding(innerPadding)
            ,
            contentAlignment = Alignment.Center){
            NoteListScreen(
                uiState = notesViewModel.uiState.collectAsStateWithLifecycle().value
            )
        }
    }

}