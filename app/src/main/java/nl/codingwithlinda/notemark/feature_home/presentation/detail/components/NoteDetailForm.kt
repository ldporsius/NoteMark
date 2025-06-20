package nl.codingwithlinda.notemark.feature_home.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.design_system.ui.theme.customTextFieldColors
import nl.codingwithlinda.notemark.design_system.ui.theme.customTextFieldShape
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailAction
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailUiState

@Composable
fun NoteDetailForm(
    uiState: NoteDetailUiState,
    onAction: (NoteDetailAction) -> Unit,
    modifier: Modifier = Modifier) {
    uiState.editNoteDto ?: return
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value =uiState.editNoteDto.title,
            onValueChange = {
                onAction(NoteDetailAction.TitleChanged(it))
            },
            colors = customTextFieldColors(),
            textStyle = LocalTextStyle.current.merge(
                MaterialTheme.typography.titleMedium
            ),
            shape = customTextFieldShape()
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.editNoteDto.content,
            onValueChange = {
                onAction(NoteDetailAction.ContentChanged(it))
            },
            colors = customTextFieldColors(),
            shape = customTextFieldShape()
        )
    }
}