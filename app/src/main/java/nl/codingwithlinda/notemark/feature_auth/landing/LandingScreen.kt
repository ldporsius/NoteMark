package nl.codingwithlinda.notemark.feature_auth.landing

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.R
import nl.codingwithlinda.notemark.design_system.components.LimitedHeightLayout
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenBackgroundInset
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenSizeHelper
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.background_landing
import nl.codingwithlinda.notemark.feature_auth.landing.components.LandingForm
import nl.codingwithlinda.notemark.feature_auth.landing.components.LandingScreenInset
import nl.codingwithlinda.notemark.feature_auth.landing.components.LandingTitle
import nl.codingwithlinda.notemark.feature_auth.landing.state.HomeAction

@Composable
fun LandingScreen(
    onGetStartedClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = background_landing
    ) { innerPadding ->

        Surface(
            modifier = modifier
                .consumeWindowInsets(innerPadding)
                .fillMaxSize()

        ) {
            val isLimitedHeigth = ScreenSizeHelper.collectScreenInfo().let {
                ScreenSizeHelper.isLimitedVertical(it)
            }
            val onAction = { action: HomeAction ->
                when (action) {
                    HomeAction.GetStartedAction -> onGetStartedClick()
                    HomeAction.HomeLoginAction -> onLoginClick()
                }
            }

            if (isLimitedHeigth) {
                LimitedHeightLayout(
                    comp1 = { LandingTitle() },
                    comp2 = { LandingForm(onAction = onAction) },
                    modifier = modifier.padding(16.dp)
                )
            } else {
                ScreenBackgroundInset(
                    backgroundImageRes = R.drawable.landing_page,
                    content = {
                        LandingScreenInset(
                            onAction = onAction,
                            modifier = Modifier
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun HomeScreenPreview() {
    NoteMarkTheme {
        LandingScreen(
            onGetStartedClick = {},
            onLoginClick = {}
        )
    }
}