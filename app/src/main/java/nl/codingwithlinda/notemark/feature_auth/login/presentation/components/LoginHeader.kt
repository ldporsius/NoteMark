package nl.codingwithlinda.notemark.feature_auth.login.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.window.core.layout.WindowWidthSizeClass

@Composable
fun LoginHeader(
    modifier: Modifier = Modifier) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    println("LOGIN ROOT WINDOW SIZE CLASS: $windowSizeClass")

    val textAlign =
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM) TextAlign.Center else TextAlign.Start

    Column(
        modifier = modifier
    ) {
        Text("Log In",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = textAlign
        )
        Text("Capture your thoughts and ideas.",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = textAlign
        )

    }
}