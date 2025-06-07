package nl.codingwithlinda.notemark.feature_auth.login.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import nl.codingwithlinda.notemark.R
import nl.codingwithlinda.notemark.design_system.ui.theme.customTextFieldColors
import nl.codingwithlinda.notemark.design_system.ui.theme.LocalButtonShape
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.feature_auth.login.presentation.state.LoginAction
import nl.codingwithlinda.notemark.feature_auth.login.presentation.state.LoginUiState

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
            placeholder = {
                Text("john.doe@example.com")
            },
            isError = uiState.emailError != null,
            supportingText = {
                uiState.emailError?.let {
                    Text(it.asString())
                }
            },
            colors = customTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )


        Text("Password",
            modifier = Modifier.align(Alignment.Start))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = {
                onAction(LoginAction.PasswordChanged(it))
            },
            isError = uiState.passwordError!= null,
            supportingText = {
                uiState.passwordError?.let {
                    Text(it.asString())
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onAction(LoginAction.TogglePasswordVisibility)
                    }
                ) {
                    if (uiState.passwordVisible) {
                        Icon(painter = painterResource(R.drawable.eye_off), contentDescription = null)
                    }
                    else {
                        Icon(painter = painterResource(R.drawable.eye), contentDescription = null)
                    }
                }
            },
            visualTransformation =
                if (uiState.passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            ,
            colors = customTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                onAction(LoginAction.Submit)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = LocalButtonShape.current,
            enabled = uiState.isLoginEnabled()
        ) {
            Text("Login")
        }

        TextButton(
            onClick = {
                onAction(LoginAction.Cancel)
            }

        ) {
            Text("Don't have an account?")
        }
    }
}

@Preview
@Composable
private fun LoginFormPreview() {
    NoteMarkTheme {
        LoginForm(
            uiState = LoginUiState(
                email = "john.doe@example.com",
                password = "password",
                passwordVisible = true
            ),
            onAction = {}
        )
    }
}