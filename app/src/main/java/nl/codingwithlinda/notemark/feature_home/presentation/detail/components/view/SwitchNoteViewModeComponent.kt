package nl.codingwithlinda.notemark.feature_home.presentation.detail.components.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.R
import nl.codingwithlinda.notemark.design_system.ui.theme.primary
import nl.codingwithlinda.notemark.design_system.ui.theme.surface
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailViewMode

@Composable
fun SwitchNoteViewModeComponent(
    mode: NoteDetailViewMode,
    onSwitch: (NoteDetailViewMode) -> Unit,
    modifier: Modifier = Modifier) {


    fun contentColor(isActive: Boolean): Color {
        return if (isActive){
            primary
        }
        else{
            Color.Black
        }
    }

    fun bgColor(isActive: Boolean): Color {
        return if (isActive){
            primary.copy(alpha = 0.1f)
        }
        else{
            Color.Transparent
        }
    }

    Row(
        modifier = modifier.background(
            color = surface,
            shape = MaterialTheme.shapes.medium
        )
            .padding(4.dp)
    ) {
        Icon(painter = painterResource(R.drawable.pencil),
            contentDescription = "detailed",
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    onSwitch(NoteDetailViewMode.EDIT)
                }
                .background(
                    color = bgColor(mode == NoteDetailViewMode.EDIT),
                    shape = MaterialTheme.shapes.medium
                )
            ,
            tint = contentColor(mode == NoteDetailViewMode.EDIT)

        )
        Icon(painter = painterResource(R.drawable.book),
            contentDescription = "read",
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    onSwitch(NoteDetailViewMode.READ)
                }
                .background(
                    color = bgColor(mode == NoteDetailViewMode.READ),
                    shape = MaterialTheme.shapes.medium
                )
            ,
            tint = contentColor(mode == NoteDetailViewMode.READ)
            ,
        )

    }

}

@Preview
@Composable
fun SwitchNoteViewModeComponentPreview() {
    var mode by remember {
        mutableStateOf(NoteDetailViewMode.VIEW)
    }
    SwitchNoteViewModeComponent(
        mode = mode,
        onSwitch = {
            mode = it
        }
    )
}
