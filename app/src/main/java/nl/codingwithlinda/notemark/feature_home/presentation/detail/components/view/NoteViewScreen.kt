package nl.codingwithlinda.notemark.feature_home.presentation.detail.components.view

import android.content.pm.ActivityInfo
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import nl.codingwithlinda.notemark.design_system.form_factors.templates.TwoColumnLayout
import nl.codingwithlinda.notemark.feature_home.presentation.detail.components.view.state.NoteViewStateAction
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailUiState
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailViewMode
import nl.codingwithlinda.notemark.feature_home.presentation.model.NoteUi

@Composable
fun NoteViewScreen(
    mode: NoteDetailViewMode,
    visibilityState: Boolean,
    onAction: (NoteViewStateAction) -> Unit,
    noteDetailUiState: NoteDetailUiState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier) {


    fun visibilityOnAction(action: NoteDetailViewMode) {
        println("VISIBILITY ON TAP CALLED. mode = $mode")
       onAction(NoteViewStateAction.ChangeMode(action))
    }
    fun visibilityOnTap() {
        println("VISIBILITY ON TAP CALLED. mode = $mode, current visibility = $visibilityState")
        onAction(NoteViewStateAction.TapAnywhere)
    }

    fun visibilityOnScroll(){
        println("VISIBILITY ON SCROLL CALLED. mode = $mode")
        onAction(NoteViewStateAction.Scroll)
    }

    val requestOrientation= remember(mode) {
        when (mode) {
            NoteDetailViewMode.VIEW -> ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            NoteDetailViewMode.EDIT -> ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            NoteDetailViewMode.READ -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }
    LocalActivity.current?.requestedOrientation   =  requestOrientation

    val scrollState = rememberScrollState()
    val nestedScroll = remember {
        object : NestedScrollConnection{
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                visibilityOnScroll()
                return super.onPreScroll(available, source)
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility( visibilityState,
                enter = fadeIn(
                    animationSpec = tween(1000)
                ),
                exit = fadeOut(
                    animationSpec = tween(1000)
                )
            ) {

                SwitchNoteViewModeComponent(
                    mode = mode,
                    onSwitch = {
                        visibilityOnAction(it)
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Box(
            modifier = modifier.padding(paddingValues)
                .safeContentPadding()
                .pointerInput(true) {
                    this.detectTapGestures {
                        visibilityOnTap()
                    }
                }
        ) {
            noteDetailUiState.note ?: return@Box

           TwoColumnLayout(
               comp1 = {
                   AnimatedVisibility(visibilityState) {
                       BackToListComponent(
                           modifier = Modifier
                               .clickable {
                                  onBack()
                               }
                               .padding(end = 16.dp, bottom = 16.dp)

                       )
                   }
               },
               comp2 = {
                   NoteViewContent(
                       note = noteDetailUiState.note,
                       modifier = Modifier
                           .fillMaxSize()
                           .nestedScroll(nestedScroll)
                           .verticalScroll(scrollState)
                   )
               },
               modifier = Modifier.fillMaxSize().padding(16.dp)
           )

        }
    }
}

@Preview
@Composable
fun NoteViewScreenPreview() {
    val note = NoteUi(
        id = "1",
        date = "2023-10-26",
        lastEdited = "2023-10-27",
        title = "Sample Note",
        content = "This is the content of the sample note."
    )
    NoteViewScreen(
        mode = NoteDetailViewMode.VIEW,
        onAction = {},
        visibilityState = true,
        noteDetailUiState = NoteDetailUiState(
            note = note),
        onBack = {},
    )
}