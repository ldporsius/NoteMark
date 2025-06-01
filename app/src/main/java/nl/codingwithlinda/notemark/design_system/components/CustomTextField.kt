package nl.codingwithlinda.notemark.design_system.components

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hasError: Boolean = false,
    modifier: Modifier = Modifier) {
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        isError = hasError,
        modifier = modifier,
    )
}