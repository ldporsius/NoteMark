package nl.codingwithlinda.notemark.feature_auth.register.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RegistrationHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text("Create account",
            style = MaterialTheme.typography.titleLarge)
        Text("Capture your thoughts and ideas.",
            style = MaterialTheme.typography.bodyLarge)
    }
}