package nl.codingwithlinda.notemark.feature_home.presentation.list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.design_system.ui.theme.onSurface

@Composable
fun NoteListEmptyScreen(
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(
        LocalContentColor provides onSurface,
        LocalTextStyle provides MaterialTheme.typography.titleMedium
    ) {
        Box(
            modifier = modifier
                .padding(top = 87.dp)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You’ve got an empty board,",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Text(
                    text = "let’s place your first note on it!",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}