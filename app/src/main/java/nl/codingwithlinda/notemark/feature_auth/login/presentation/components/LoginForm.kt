package nl.codingwithlinda.notemark.feature_auth.login.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.codingwithlinda.notemark.design_system.components.CustomTextField
import nl.codingwithlinda.notemark.design_system.ui.theme.CustomTextFieldColors
import nl.codingwithlinda.notemark.design_system.ui.theme.LocalButtonShape
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
        Text("Email",
            modifier = Modifier.align(Alignment.Start))
        OutlinedTextField(
            value = uiState.email,
            onValueChange = {
                onAction(LoginAction.EmailChanged(it))
            },
            colors = CustomTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )


        Text("Password",
            modifier = Modifier.align(Alignment.Start))
        CustomTextField(
            value = uiState.password,
            onValueChange = {
                onAction(LoginAction.PasswordChanged(it))
            },
            label = "password",
            errorMessage = uiState.passwordError?.asString(),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                onAction(LoginAction.Submit)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = LocalButtonShape.current
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