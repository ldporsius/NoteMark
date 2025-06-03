package nl.codingwithlinda.notemark.design_system.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    modifier: Modifier = Modifier) {

    val hasError = errorMessage != null

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(label)
        },
        trailingIcon = trailingIcon,
        isError = hasError,
        supportingText = {
            errorMessage?.let {
                Text(it)
            }
        },
        modifier = modifier,
    )
}