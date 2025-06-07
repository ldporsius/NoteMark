package nl.codingwithlinda.notemark.design_system.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.codingwithlinda.notemark.design_system.ui.theme.customTextFieldColors

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    modifier: Modifier = Modifier) {

    val hasError = errorMessage != null

    val colors = customTextFieldColors()

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            label?.let {
                Text(label)
            }
        },
        placeholder = {
            placeholder?.let {
                Text(it)
            }
        },
        trailingIcon = trailingIcon,
        isError = hasError,
        supportingText = {
            errorMessage?.let {
                Text(it)
            }
        },
        colors = colors,
        modifier = modifier,
    )
}