package nl.codingwithlinda.notemark.feature_auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoginHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text("Log In", style = MaterialTheme.typography.headlineLarge)
        Text("Capture your thoughts and ideas.", style = MaterialTheme.typography.bodyMedium)

    }
}