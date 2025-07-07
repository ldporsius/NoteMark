package nl.codingwithlinda.notemark.feature_home.presentation.detail.components.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.feature_home.presentation.model.NoteUi

@Composable
fun NoteViewContent(
    note: NoteUi,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = note.title,
            style = MaterialTheme.typography.headlineLarge)

        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.Start)

        ) {
            Column {
                Text("Date created")
                Text(text = note.date,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
           Column {
               Text("Last edited")
               Text(
                   text = note.lastEdited,
                   style = MaterialTheme.typography.bodyMedium,
                   fontWeight = FontWeight.Bold
               )
           }
        }

        HorizontalDivider()
        Text(text = note.content,
            style = MaterialTheme.typography.bodyMedium)

    }
}

@Preview
@Composable
fun NoteViewContentPreview() {
    val note = NoteUi(
        id = "1",
        date = "01/01/2023",
        lastEdited = "02/01/2023",
        title = "Sample Note",
        content = "This is the content of the sample note."
    )
    NoteViewContent(note = note,
        modifier = Modifier.fillMaxSize())
}

