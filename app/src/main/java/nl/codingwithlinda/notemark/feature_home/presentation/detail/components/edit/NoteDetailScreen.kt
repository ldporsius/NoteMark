package nl.codingwithlinda.notemark.feature_home.presentation.detail.components.edit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.codingwithlinda.notemark.design_system.components.ConfirmDialog
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenSizeHelper
import nl.codingwithlinda.notemark.design_system.form_factors.templates.ThreeColumnsLayout
import nl.codingwithlinda.notemark.design_system.ui.theme.surface
import nl.codingwithlinda.notemark.feature_home.presentation.detail.components.common.NoteDetailCancelComponent
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailAction
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoteDetailScreen(
    uiState: NoteDetailUiState,
    onAction: (NoteDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {


    uiState.editNoteDto ?: return

    BackHandler {
        onAction(NoteDetailAction.ConfirmCancelDialog)
    }
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
                    NoteDetailFormCompactHeight(
                        uiState = uiState,
                        onAction = onAction,
                        modifier = Modifier
                    )
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
            .imePadding()
            .systemBarsPadding()
            .windowInsetsPadding(WindowInsets.displayCutout)
        ,
        topBar = { topBar() }
    ) {
        padding ->

        Box(modifier = Modifier
            .padding(padding)
            .then(modifier)
        ){
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