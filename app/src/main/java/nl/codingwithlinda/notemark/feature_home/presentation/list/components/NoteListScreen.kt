package nl.codingwithlinda.notemark.feature_home.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.rememberNavBackStack
import nl.codingwithlinda.notemark.design_system.components.ConfirmDialog
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenSizeHelper
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.backgroundGradientGrey
import nl.codingwithlinda.notemark.feature_home.data.local.dummyUiNotes
import nl.codingwithlinda.notemark.feature_home.data.local.olderNotes
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListAction
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListUiState
import nl.codingwithlinda.notemark.feature_home.presentation.model.toUi
import org.threeten.bp.ZonedDateTime

@Composable
fun NoteListScreen(
    uiState: NoteListUiState,
    onAction: (NoteListAction) -> Unit
    ) {

    val isVertical =
        ScreenSizeHelper.canDisplayVertical(
            ScreenSizeHelper.collectScreenInfo()
        )

    val columnCount = remember(isVertical) {
        if (isVertical) 2 else 3
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundGradientGrey)
    ) {


        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(columnCount),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            contentPadding = PaddingValues(bottom = 48.dp)
        ) {
            items(uiState.notes) { noteUi ->
                NoteListItem(
                    note = noteUi,
                onNoteClick = {
                    onAction(it)
                },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    if (uiState.showDeleteConfirmationDialog){
        ConfirmDialog(
            title = "Delete note",
            message = "Are you sure you want to delete this note?",
            confirmText = "Delete",
            onDismiss = {
                onAction(NoteListAction.DismissDeleteConfirmationDialog)
            },
            onConfirm = {
                onAction(NoteListAction.DeleteNoteAction)
            }
        )
    }
}

@PreviewScreenSizes
@Composable
private fun NoteListScreenPreview() {
    NoteMarkTheme {
        NoteListScreen(
            uiState = NoteListUiState(
                notes = dummyUiNotes
            ),
            onAction = {}
        )
    }
}