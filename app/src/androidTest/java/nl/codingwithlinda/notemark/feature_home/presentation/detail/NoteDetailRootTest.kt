package nl.codingwithlinda.notemark.feature_home.presentation.detail

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import nl.codingwithlinda.notemark.tests.fake_classes.FakeNoteRepository


@OptIn(ExperimentalSharedTransitionApi::class)
@RunWith(AndroidJUnit4::class)
class NoteDetailRootTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Test
    fun noteDetailRoot_initialDisplay_showsViewMode() {
        fakeNoteRepository = FakeNoteRepository()
        val noteId = "testId"
        var navBackCalled = false

        /*composeTestRule.setContent {
            SharedTransitionLayout {
                // `this` is the SharedTransitionScope
                // `it` is the AnimatedContentScope
                NoteDetailRoot(
                    sharedTransitionScope = this,
                    animatedContentScope = it,
                    noteId = noteId,
                    noteRepository = fakeNoteRepository,
                    navBack = { navBackCalled = true }
                )
            }
        }
*/
        // TODO: Add assertions to verify that NoteViewScreen is initially displayed
        // For example, check for text from the sample note.
        // We'''ll also need to handle the ViewModel creation and its initial state.
    }

    // TODO: Add more tests for different interactions:
    // - Switching to EDIT mode
    // - Switching to READ mode
    // - Visibility changes on tap/scroll
    // - Navigating back
}
