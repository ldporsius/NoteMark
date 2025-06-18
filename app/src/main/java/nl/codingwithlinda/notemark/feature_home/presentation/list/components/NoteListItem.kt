package nl.codingwithlinda.notemark.feature_home.presentation.list.components

import android.service.autofill.Validators.or
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.design_system.form_factors.Orientation
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenSizeHelper
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenType
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.customCardColors
import nl.codingwithlinda.notemark.design_system.ui.theme.primary
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListAction
import nl.codingwithlinda.notemark.feature_home.presentation.model.NoteUi
import nl.codingwithlinda.notemark.feature_home.presentation.model.limitContent

@Composable
fun NoteListItem(
    note: NoteUi,
    onNoteClick: (action: NoteListAction) -> Unit,
    modifier: Modifier = Modifier) {

    val hasRoomHorizontal = ScreenSizeHelper.collectScreenInfo().let {
                (it.width_height.width == ScreenType.MEDIUM && it.orientation == Orientation.PORTRAIT)
                ||
                (it.width_height.width == ScreenType.EXPANDED && it.width_height.height == ScreenType.EXPANDED)

    }

    var charLimit by remember {
        mutableIntStateOf(150)
    }
    BoxWithConstraints(
        modifier = modifier
            .pointerInput(true){
                this.detectTapGestures(
                    onTap = {
                        onNoteClick(NoteListAction.EditNoteAction(noteId = note.id))
                    },
                    onLongPress = {
                        onNoteClick(NoteListAction.ShowDeleteConfirmationDialog(noteId = note.id))
                    }
                )
            }
        ,
    ) {
        val width = this.maxWidth
        if (hasRoomHorizontal){
            charLimit = 250
        }
        else{
            charLimit = 150
        }
        Card(
            colors = customCardColors()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    note.date.uppercase(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = primary
                )

                Text(
                    note.title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    note.limitContent(charLimit).content,
                    style = MaterialTheme.typography.bodySmall,
                    onTextLayout = {

                        val lineAtIndex = it.getLineForOffset(150)
                        println("lineAtIndex: $lineAtIndex")
                        //if (lineAtIndex < 5) charLimit = 250 else charLimit = 150
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun NoteListItemPreview() {
    NoteMarkTheme {
        NoteListItem(
            note = NoteUi(
                id = "1",
                date = "14 jul",
                title = "My first note",
                content = "This is my first note"
            ),
            onNoteClick = {}
        )
    }

}