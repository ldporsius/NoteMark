package nl.codingwithlinda.notemark.feature_home.presentation.previews

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.feature_home.presentation.list.components.NoteListScreen
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListUiState
import nl.codingwithlinda.notemark.tests.dummy_content.dummyUiNotes

@OptIn(ExperimentalSharedTransitionApi::class)
@PreviewScreenSizes
@Composable
private fun NoteListScreenPreview() {
    NoteMarkTheme {
        SharedTransitionLayout {
            AnimatedContent(true) {

                it
                NoteListScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this,
                    uiState = NoteListUiState(
                        notes = dummyUiNotes
                    ),
                    onAction = {},
                    onEditNote = {}
                )
            }
        }
    }
}