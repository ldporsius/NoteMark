package nl.codingwithlinda.notemark.feature_home.presentation.detail.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.util.ObserveAsEvents
import nl.codingwithlinda.notemark.core.util.SnackBarController
import nl.codingwithlinda.notemark.design_system.components.ConfirmDialog
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenSizeHelper
import nl.codingwithlinda.notemark.design_system.form_factors.templates.ThreeColumnsLayout
import nl.codingwithlinda.notemark.design_system.ui.theme.customOutlinedTextFieldColors
import nl.codingwithlinda.notemark.design_system.ui.theme.customTextFieldColors
import nl.codingwithlinda.notemark.design_system.ui.theme.customTextFieldShape
import nl.codingwithlinda.notemark.design_system.ui.theme.surface
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailAction
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailUiState

@Composable
fun NoteDetailScreen(
    uiState: NoteDetailUiState,
    onAction: (NoteDetailAction) -> Unit,
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    ObserveAsEvents(SnackBarController.events){ event ->
        scope.launch {
            event.action?.action?.invoke()
            event.message.asString(context).let { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
    uiState.editNoteDto ?: return

    val isLimitedHeight = ScreenSizeHelper.isLimitedVertical(
        ScreenSizeHelper.collectScreenInfo()
    )

    @Composable
    fun topBar() {
        if (isLimitedHeight) {
            Unit
        }
        else{
            NoteDetailTopBar(
                isSaving = uiState.isSaving,
                onAction = onAction,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    fun form(){
        NoteDetailForm(
            uiState = uiState,
            onAction = onAction,
            modifier = Modifier
                .imePadding()
                .background(color = surface)
                .verticalScroll(rememberScrollState())
        )
    }

    @Composable
    fun content() {
        if (isLimitedHeight) {
            ThreeColumnsLayout(
                comp1 = {
                    NoteDetailCancelComponent(
                        onClick = {
                            onAction(NoteDetailAction.ConfirmCancelDialog)
                        },
                        modifier = Modifier
                    )
                },
                comp2 = {
                    form()
                },
                comp3 = {
                    NoteDetailSaveComponent(
                        isSaving = uiState.isSaving,
                        onClick = {
                            onAction(NoteDetailAction.SaveAction)
                        }
                    )
                }
            )
        }
        else{
            form()
        }
    }
    Scaffold(
        containerColor = surface,
        modifier = Modifier
            .fillMaxSize()
            .background(color = surface)
            .safeContentPadding()
        ,
        topBar = { topBar() }
    ) {
        padding ->

        Box(modifier = Modifier.padding(padding)){
           content()
        }

        
        if (uiState.showConfirmCancelDialog) {
            ConfirmDialog(
                onConfirm = {
                    onAction(NoteDetailAction.CancelAction)
                },
                onDismiss = {
                    onAction(NoteDetailAction.DismissCancelDialog)
                },
                title = "Discard Changes?",
                message = "You have unsaved changes.\nIf you discard now, all changes will be lost.",
                confirmText = "Discard",
                dismissText = "Keep editing",
                modifier = Modifier,
            )
        }

    }
}