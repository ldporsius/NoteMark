package nl.codingwithlinda.notemark.feature_home.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.design_system.components.ConfirmDialog
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenSizeHelper
import nl.codingwithlinda.notemark.design_system.ui.theme.backgroundGradientGrey
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListAction
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListUiState

@Composable
fun NoteListScreen(
    uiState: NoteListUiState,
    onAction: (NoteListAction) -> Unit,
    onEditNote: (String) -> Unit
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
            contentPadding = PaddingValues(bottom = 48.dp),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            items(uiState.notes) { noteUi ->
                NoteListItem(
                    note = noteUi,
                    onNoteClick = {
                        onAction(it)
                    },
                    onNoteEdit = {
                        onEditNote(it)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    if (uiState.showDeleteConfirmationDialog){
        ConfirmDialog(
            title = "Delete note",
            message = "Are you sure you want to delete this note? This cannot be undone.",
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

