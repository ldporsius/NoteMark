package nl.codingwithlinda.notemark.feature_home.presentation.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.customCardColors
import nl.codingwithlinda.notemark.design_system.ui.theme.primary
import nl.codingwithlinda.notemark.feature_home.presentation.model.NoteUi
import nl.codingwithlinda.notemark.feature_home.presentation.model.limitContent

@Composable
fun NoteListItem(
    note: NoteUi,
    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
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
                note.limitContent(150).content,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
private fun NoteListItemPreview() {
    NoteMarkTheme {
        NoteListItem(
            note = NoteUi(
                date = "14 jul",
                title = "My first note",
                content = "This is my first note"
            )
        )
    }

}