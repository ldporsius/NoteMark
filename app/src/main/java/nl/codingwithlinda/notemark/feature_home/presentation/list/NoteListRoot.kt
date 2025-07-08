package nl.codingwithlinda.notemark.feature_home.presentation.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.util.ObserveAsEvents
import nl.codingwithlinda.notemark.design_system.components.CustomFAB
import nl.codingwithlinda.notemark.design_system.ui.theme.onPrimary
import nl.codingwithlinda.notemark.design_system.ui.theme.surface
import nl.codingwithlinda.notemark.design_system.ui.theme.surfaceLowest
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.list.components.NoteListEmptyScreen
import nl.codingwithlinda.notemark.feature_home.presentation.list.components.NoteListScreen
import nl.codingwithlinda.notemark.feature_home.presentation.list.components.NoteListTopBar
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListAction
import nl.codingwithlinda.notemark.core.navigation.dto.EditNoteDto
import nl.codingwithlinda.notemark.feature_home.presentation.util.userNameAvatar

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NoteListRoot(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: NoteListViewModel,
    sessionManager: SessionManager,
    onNewNote: (String) -> Unit,
    onEditNote: (String) -> Unit,
    goToSettings: () -> Unit
) {
    var userAvatar by remember {
        mutableStateOf("")
    }
    ObserveAsEvents(sessionManager.loginState) {
        userAvatar = userNameAvatar(it.userId)
    }

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
                userAvatar = userAvatar,
                onAction = {
                    goToSettings()
                }
            )
        },
        floatingActionButton = {
            CustomFAB(
                onClick = {
                    viewModel.createNewNote().also {
                        onNewNote(it.id)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add note",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                        .background(Color.Transparent)
                )
            }
        }
    ) {innerPadding ->
        Box(Modifier
            .fillMaxSize()
            .padding(innerPadding)
            ,
            contentAlignment = Alignment.TopCenter){

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            AnimatedContent(targetState = uiState.notes.isEmpty()) {
                if (it){
                    NoteListEmptyScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = surface)
                    )
                }
                else{
                    NoteListScreen(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                        onEditNote = onEditNote,
                        onAction = viewModel::onAction
                    )
                }
            }
        }
    }
}