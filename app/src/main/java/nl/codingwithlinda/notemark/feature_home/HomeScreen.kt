package nl.codingwithlinda.notemark.feature_home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier) {

    Column {

        Text("This is the home screen", modifier = modifier)
        Button(onClick = { onLoginClick() }) {
            Text("Login")

        }
    }
}