package nl.codingwithlinda.notemark.feature_home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.util.ObserveAsEvents
import nl.codingwithlinda.notemark.design_system.ui.theme.onPrimary
import nl.codingwithlinda.notemark.design_system.ui.theme.surfaceLowest
import nl.codingwithlinda.notemark.feature_home.data.local.NoteRepositoryImpl
import nl.codingwithlinda.notemark.feature_home.presentation.list.NoteListViewModel
import nl.codingwithlinda.notemark.feature_home.presentation.list.components.NoteListScreen
import nl.codingwithlinda.notemark.feature_home.presentation.list.components.NoteListTopBar
import nl.codingwithlinda.notemark.feature_home.presentation.util.userNameAvatar

@Composable
fun HomeRoot(
    sessionManager: SessionManager
) {
    var userAvatar by remember {
        mutableStateOf("")
    }
    ObserveAsEvents(sessionManager.loginState) {
        userAvatar = userNameAvatar(it.userId)
    }
    val repo = NoteRepositoryImpl()
    val notesViewModel = viewModel<NoteListViewModel>(
        factory = viewModelFactory {
            initializer {
                NoteListViewModel(repo)
            }
        }
    )
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(surfaceLowest)
            .safeDrawingPadding()
        ,
        containerColor = surfaceLowest,
        contentColor = onPrimary,
        topBar = {
            NoteListTopBar(
                userAvatar = userAvatar
            )
        }
    ) {innerPadding ->
        Box(Modifier
            .fillMaxSize()
            .padding(innerPadding)
            ,
            contentAlignment = Alignment.Center){
            NoteListScreen(
                uiState = notesViewModel.uiState.collectAsStateWithLifecycle().value
            )
        }
    }

}