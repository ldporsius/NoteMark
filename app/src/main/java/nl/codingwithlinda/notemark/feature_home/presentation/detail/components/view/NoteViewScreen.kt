package nl.codingwithlinda.notemark.feature_home.presentation.detail.components.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailViewMode
import nl.codingwithlinda.notemark.feature_home.presentation.model.NoteUi

@Composable
fun NoteViewScreen(
    note: NoteUi,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier) {

    var mode by remember {
        mutableStateOf(NoteDetailViewMode.VIEW)
    }

    var visibilityState by remember {
        mutableStateOf(true)
    }

    fun visibilityFiniteState() {
       when(mode){
           NoteDetailViewMode.VIEW -> visibilityState = true
           NoteDetailViewMode.EDIT -> visibilityState = true
           NoteDetailViewMode.READ -> visibilityState = !visibilityState
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
                            mode = it
                            visibilityFiniteState()
                        }
                    )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Box(
            modifier = modifier.padding(paddingValues)
                .pointerInput(true){
                    this.detectTapGestures {
                        visibilityFiniteState()
                    }
                }
        ) {
            NoteViewContent(
                note = note,
                modifier = Modifier.fillMaxSize()
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
        note = note,
        onEdit = {}
    )
}