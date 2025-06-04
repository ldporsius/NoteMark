package nl.codingwithlinda.notemark.feature_home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import nl.codingwithlinda.notemark.R
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenBackgroundInset
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.background_landing
import nl.codingwithlinda.notemark.feature_home.components.HomeScreenInset
import nl.codingwithlinda.notemark.feature_home.state.HomeAction

@Composable
fun HomeScreen(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = background_landing
    ) { innerPadding ->

        Surface(
            modifier = modifier.fillMaxSize().padding(innerPadding)
        ) {
            ScreenBackgroundInset(
                backgroundImageRes = R.drawable.landing_page,
                windowSize = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass,
                content = {
                    HomeScreenInset(
                        onAction = {
                            when (it) {
                                HomeAction.GetStartedAction -> {/*TODO nav to register*/
                                }

                                HomeAction.LoginAction -> onLoginClick()
                            }
                        },
                        modifier = Modifier

                    )
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@PreviewScreenSizes
@Composable
private fun HomeScreenPreview() {
    NoteMarkTheme {
        HomeScreen(
            onLoginClick = {}
        )
    }
}