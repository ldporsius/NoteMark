package nl.codingwithlinda.notemark.feature_home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreenInset(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text("Your Own Collection of Notes",
            style = MaterialTheme.typography.headlineLarge,)

        Text(text = "Capture your thoughts and ideas.",
            style = MaterialTheme.typography.bodyLarge)
        Button(onClick = { /*TODO*/ }
            ,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get started")
        }
        Button(onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}