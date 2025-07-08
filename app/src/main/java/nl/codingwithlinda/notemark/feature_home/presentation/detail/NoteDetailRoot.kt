package nl.codingwithlinda.notemark.feature_home.presentation.detail

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.util.ObserveAsEvents
import nl.codingwithlinda.notemark.core.util.SnackBarController
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_home.presentation.detail.components.edit.NoteDetailScreen
import nl.codingwithlinda.notemark.feature_home.presentation.detail.components.view.NoteViewScreen
import nl.codingwithlinda.notemark.feature_home.presentation.detail.components.view.state.NoteViewStateAction
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailViewMode

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NoteDetailRoot(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    noteId: String,
    noteRepository: NoteRepository,
    navBack: () -> Unit,
) {
    var mode by rememberSaveable {
        mutableStateOf(NoteDetailViewMode.VIEW)
    }

    var visibilityState by rememberSaveable {
        mutableStateOf(true)
    }

    val autohideTimer = remember(visibilityState) {
        flow {
            while (true) {
                kotlinx.coroutines.delay(1000)
                emit(Unit)
            }
        }
    }

    fun modeOnAction(viewMode: NoteDetailViewMode) {
        println("MODE ON ACTION CALLED. current mode = $mode. changing to: $viewMode")
        val newMode = when(viewMode){
            NoteDetailViewMode.VIEW -> NoteDetailViewMode.VIEW
            NoteDetailViewMode.EDIT -> NoteDetailViewMode.EDIT
            NoteDetailViewMode.READ -> {
                if (mode != NoteDetailViewMode.READ) NoteDetailViewMode.READ
                else NoteDetailViewMode.VIEW
            }
        }
       mode = newMode
    }

    fun visibilityOnAction(viewMode: NoteDetailViewMode) {
        println("VISIBILITY ON ACTION CALLED. current mode = $mode. changing to: $viewMode")
        val visibility = when(viewMode){
            NoteDetailViewMode.VIEW -> true
            NoteDetailViewMode.EDIT -> false
            NoteDetailViewMode.READ -> false
        }
        visibilityState = visibility
    }

    fun visibilityOnTap() {
        println("VISIBILITY ON TAP CALLED. mode = $mode, current visibility = $visibilityState")
        val visibility = when(mode){
            NoteDetailViewMode.VIEW -> true
            NoteDetailViewMode.EDIT -> false
            NoteDetailViewMode.READ -> !visibilityState
        }
        println("VISIBILITY ON TAP CALLED. setting visibility to: $visibility")
        visibilityState = visibility
    }

    fun visibilityOnScroll(){
        println("VISIBILITY ON SCROLL CALLED. mode = $mode")
        val visibility = when(mode){
            NoteDetailViewMode.VIEW -> true
            NoteDetailViewMode.EDIT -> true
            NoteDetailViewMode.READ -> false
        }
        visibilityState = visibility
    }
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
        mode = NoteDetailViewMode.VIEW
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
    LaunchedEffect(visibilityState, mode){
        var count = 0
        if (visibilityState){
            autohideTimer.collect{
                count ++
                if (count > 5) {
                    visibilityOnAction(NoteDetailViewMode.READ)
                    count = 0
                }
            }
        }
    }

    with(sharedTransitionScope) {
        AnimatedContent(mode) {
            if (it == NoteDetailViewMode.EDIT) {
                NoteDetailScreen(
                    uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                    onAction = viewModel::onAction,
                    modifier = Modifier.sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = noteId),
                        animatedVisibilityScope = animatedContentScope
                    )
                )
            }
            else{
                NoteViewScreen(
                    mode = mode,
                    visibilityState = visibilityState,
                    onAction = {action ->
                        when(action){
                            is NoteViewStateAction.ChangeMode -> {
                                modeOnAction(action.mode)
                                visibilityOnAction(action.mode)
                            }
                            NoteViewStateAction.Scroll -> {
                                visibilityOnScroll()
                            }
                            NoteViewStateAction.TapAnywhere -> {
                                visibilityOnTap()
                            }
                        }

                    },
                    noteDetailUiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                    onBack = {
                        navBack()
                    },
                    modifier = Modifier.sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = noteId),
                        animatedVisibilityScope = animatedContentScope
                    )
                )

            }
        }

    }
}