package nl.codingwithlinda.notemark.feature_auth.login.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LoginHeader(
    textAlign: TextAlign,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text("Log In",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = textAlign
        )
        Text("Capture your thoughts and ideas.",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = textAlign
        )

    }
}