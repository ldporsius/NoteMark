package nl.codingwithlinda.notemark.feature_auth.landing.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.design_system.components.CustomColoredButton
import nl.codingwithlinda.notemark.design_system.components.LimitedHeightLayout
import nl.codingwithlinda.notemark.design_system.form_factors.ScreenSizeHelper
import nl.codingwithlinda.notemark.design_system.ui.theme.LocalButtonShape
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.onSurface
import nl.codingwithlinda.notemark.feature_auth.landing.state.HomeAction

@Composable
fun LandingScreenInset(
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier) {
           Column(
               modifier
           ) {
               LandingTitle()
               LandingForm(onAction)
           }

}
@Composable
fun LandingForm(
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Button(
            onClick = { onAction(HomeAction.GetStartedAction) },
            modifier = Modifier.fillMaxWidth(),
            shape = LocalButtonShape.current
        ) {
            Text("Get started")
        }
        CustomColoredButton(
            color = Color.White,
            text = "Log In",
            onClick = { onAction(HomeAction.HomeLoginAction) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Composable
fun LandingTitle(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides
                    TextStyle.Default.copy(
                        color = onSurface
                    )
        ) {
            Text(
                "Your Own Collection of Notes",
                style = MaterialTheme.typography.headlineLarge,
            )

            Text(
                text = "Capture your thoughts and ideas.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}