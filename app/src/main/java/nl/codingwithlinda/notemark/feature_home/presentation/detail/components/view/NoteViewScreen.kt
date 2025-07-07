package nl.codingwithlinda.notemark.feature_home.presentation.detail.components.view

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
import androidx.compose.ui.tooling.preview.Preview
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailViewMode
import nl.codingwithlinda.notemark.feature_home.presentation.model.NoteUi

@Composable
fun NoteViewScreen(
    note: NoteUi,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier) {
    //title
    //date created
    //last edited
    //note text
    //switch mode view/read

    var mode by remember {
        mutableStateOf(NoteDetailViewMode.VIEW)
    }
    Scaffold(
        floatingActionButton = {
            SwitchNoteViewModeComponent(
                mode = mode,
                onSwitch = {
                    mode = it
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Box(
            modifier = modifier.padding(paddingValues)
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