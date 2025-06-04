package nl.codingwithlinda.notemark.feature_home.components

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
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.onSurface
import nl.codingwithlinda.notemark.feature_home.state.HomeAction

@Composable
fun HomeScreenInset(
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier) {


    NoteMarkTheme {
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
            Button(
                onClick = { onAction(HomeAction.GetStartedAction) },
                modifier = Modifier.fillMaxWidth()
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

}