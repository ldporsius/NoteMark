package nl.codingwithlinda.notemark.feature_home.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.codingwithlinda.notemark.design_system.ui.theme.backgroundGradientGrey
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListUiState

@Composable
fun NoteListScreen(
    uiState: NoteListUiState,
    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundGradientGrey)
    ) {


        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
        ) {
            items(uiState.notes) {
                NoteListItem(note = it)
            }
        }
    }
}