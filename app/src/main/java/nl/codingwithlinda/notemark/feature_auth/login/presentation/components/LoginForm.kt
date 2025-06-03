package nl.codingwithlinda.notemark.feature_auth.login.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.codingwithlinda.notemark.design_system.components.CustomTextField
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.feature_auth.login.state.LoginAction
import nl.codingwithlinda.notemark.feature_auth.login.state.LoginUiState

@Composable
fun LoginForm(
    uiState: LoginUiState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = uiState.email,
            onValueChange = {
                onAction(LoginAction.EmailChanged(it))
            },
            label = "Email",
            errorMessage = uiState.emailError?.asString(),
            modifier = Modifier.fillMaxWidth()
        )
        CustomTextField(
            value = uiState.password,
            onValueChange = {
                onAction(LoginAction.PasswordChanged(it))
            },
            label = "Email",
            errorMessage = uiState.passwordError?.asString(),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                onAction(LoginAction.Submit)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}

@Preview
@Composable
private fun LoginFormPreview() {
    NoteMarkTheme {
        LoginForm(
            uiState = LoginUiState(),
            onAction = {}
        )
    }
}