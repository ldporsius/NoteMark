package nl.codingwithlinda.notemark.feature_home.presentation.detail.components.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
    val keyboard = LocalSoftwareKeyboardController.current

    var maxSizeModifier = remember {
        Modifier.fillMaxSize()
    }


    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = uiState.editNoteDto.title,
                selection = TextRange(uiState.editNoteDto.title.length),
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
                .fillMaxSize()
                .onFocusChanged(){
                    println("ON TITLE FOCUS CHANGED: $it")
                    maxSizeModifier = Modifier.fillMaxSize()
                }
                .then(
                    maxSizeModifier
                )
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
            modifier = Modifier
                .fillMaxSize()
            ,
            value = uiState.editNoteDto.content,
            onValueChange = {
                onAction(NoteDetailAction.ContentChanged(it))
            },
            colors = customTextFieldColors(),
            shape = customTextFieldShape()
        )
    }
}