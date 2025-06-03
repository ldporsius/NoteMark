package nl.codingwithlinda.notemark.design_system.form_factors

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.window.core.layout.WindowWidthSizeClass.Companion.EXPANDED
import androidx.window.core.layout.WindowWidthSizeClass.Companion.MEDIUM
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.feature_auth.login.presentation.components.LoginForm
import nl.codingwithlinda.notemark.feature_auth.login.presentation.components.LoginHeader
import nl.codingwithlinda.notemark.feature_auth.login.state.LoginUiState

@Composable
fun ScreenTwoComposables(
    comp1: @Composable () -> Unit,
    comp2: @Composable () -> Unit,
    modifier: Modifier = Modifier) {


    val windowSizeInfo = currentWindowAdaptiveInfo().windowSizeClass
    val widthSize = windowSizeInfo.windowWidthSizeClass
    val isWideScreen = widthSize == EXPANDED
    val isMediumScreen = widthSize == MEDIUM

    val comp1Id = "comp1"
    val comp2Id = "comp2"
     val constraintSet = if (isWideScreen) {
        // Constraints for a wider screen (e.g., side-by-side)
        ConstraintSet {
            val comp1Ref = createRefFor(comp1Id)
            val comp2Ref = createRefFor(comp2Id)

            constrain(comp1Ref) {
                start.linkTo(parent.start, margin = 16.dp)
                top.linkTo(parent.top, margin = 16.dp)
                //bottom.linkTo(parent.bottom, margin = 16.dp)
                width = Dimension.percent(0.4f) // Example: Header takes 40% of width
            }
            constrain(comp2Ref) {
                start.linkTo(comp1Ref.end, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                top.linkTo(parent.top, margin = 16.dp)
                //bottom.linkTo(parent.bottom, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        }
    } else {
        // Constraints for a narrower screen (e.g., stacked)
        ConstraintSet {
            val comp1Ref = createRefFor(comp1Id)
            val comp2Ref = createRefFor(comp2Id)

            constrain(comp1Ref) {
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
                top.linkTo(parent.top, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
            constrain(comp2Ref) {
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
                top.linkTo(comp1Ref.bottom, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints // Allow form to take remaining height
            }
        }
    }


    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .layoutId(comp1Id)
        ){
            comp1()
        }
        Box(modifier = Modifier
            .layoutId(comp2Id)
        ){
           comp2()

        }
    }
}

@PreviewScreenSizes
@Composable
private fun LoginScreenBoxConstraintsPreview() {
    NoteMarkTheme {
        ScreenTwoComposables(
            comp1 = {
                LoginHeader(
                    textAlign = TextAlign.Center
                )
            },
            comp2 = {
                LoginForm(
                    uiState = LoginUiState(),
                    onAction = {},
                    modifier = Modifier
                        .widthIn(min= 360.dp, max = 460.dp)
                        .padding(16.dp)
                )
            },
            modifier = Modifier.fillMaxSize()
        )

    }
}