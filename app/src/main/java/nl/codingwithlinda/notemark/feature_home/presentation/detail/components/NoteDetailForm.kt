package nl.codingwithlinda.notemark.feature_home.presentation.detail.components

import android.R.attr.text
import android.util.Log.v
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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

    val focusRequester = remember { FocusRequester() }

    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = uiState.editNoteDto.title,
                selection = TextRange(uiState.editNoteDto.title.length)
            )
        )
    }

    LaunchedEffect(Unit){
        focusRequester.requestFocus()
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = textFieldValue,
            onValueChange = {
                onAction(NoteDetailAction.TitleChanged(it.text))
                textFieldValue = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
            ,
            textStyle = LocalTextStyle.current.merge(
                MaterialTheme.typography.titleMedium
            ),
            colors = customTextFieldColors(),
            shape = customTextFieldShape(),
            singleLine = true
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