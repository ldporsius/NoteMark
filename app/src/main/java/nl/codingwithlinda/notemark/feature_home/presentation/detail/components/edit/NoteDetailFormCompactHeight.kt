package nl.codingwithlinda.notemark.feature_home.presentation.detail.components.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.design_system.ui.theme.customTextFieldColors
import nl.codingwithlinda.notemark.design_system.ui.theme.customTextFieldShape
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailAction
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailUiState

@Composable
fun NoteDetailFormCompactHeight(
    uiState: NoteDetailUiState,
    onAction: (NoteDetailAction) -> Unit,
    modifier: Modifier = Modifier) {
    uiState.editNoteDto ?: return

    val focusRequester = remember { FocusRequester() }

    var showTitleInputDialog: Boolean by remember {
        mutableStateOf(false)
    }

    var showContentInputDialog: Boolean by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit){
        focusRequester.requestFocus()
    }
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(text = uiState.editNoteDto.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                showTitleInputDialog = true
            }
        )
        HorizontalDivider()

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showContentInputDialog = true
                }
            ,
            text= uiState.editNoteDto.content,
        )

        HorizontalDivider()
    }

    if (showTitleInputDialog){
        FormInputDialog(
            inialText = uiState.editNoteDto.title,
            onValueChange = {
                onAction(NoteDetailAction.TitleChanged(it))
            },
            onDismiss = {
                showTitleInputDialog = false
            },
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceContainerLowest)
        )
    }

    if (showContentInputDialog){
        FormInputDialog(
            inialText = uiState.editNoteDto.content,
            onValueChange = {
                onAction(NoteDetailAction.ContentChanged(it))
            },
            onDismiss = {
                showContentInputDialog = false
            },
            singleLine = false,
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceContainerLowest)
        )
    }
}

@Composable
fun FormInputDialog(
    inialText: String,
    onValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier) {

    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = inialText,
                selection = TextRange(inialText.length),
            )
        )
    }

    TextFieldDialog(
        onDismiss = { onDismiss() },
        textTield = {
            TextField(
                value = textFieldValue,
                onValueChange = {
                    onValueChange(it.text)
                    textFieldValue = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    ,
                textStyle = LocalTextStyle.current.merge(
                    MaterialTheme.typography.titleMedium
                ),
                colors = customTextFieldColors(),
                shape = customTextFieldShape(),
                singleLine = singleLine
            )
        },
        modifier = modifier
    )

}