package nl.codingwithlinda.notemark.feature_auth.register.presentation.components

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
import nl.codingwithlinda.notemark.design_system.ui.theme.CustomTextFieldColors
import nl.codingwithlinda.notemark.design_system.ui.theme.LocalButtonShape
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.feature_auth.register.presentation.state.RegistrationAction
import nl.codingwithlinda.notemark.feature_auth.register.presentation.state.RegistrationUiState

@Composable
fun RegistrationForm(
    uiState: RegistrationUiState,
    onAction: (RegistrationAction) -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Username")
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {  },
            singleLine = true,
            colors = CustomTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        Text("Email",
            modifier = Modifier.align(Alignment.Start))
        OutlinedTextField(
            value = uiState.email,
            onValueChange = {
                onAction(RegistrationAction.EmailAction(it))
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
            colors = CustomTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        Text("Password",
            modifier = Modifier.align(Alignment.Start))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = {
                onAction(RegistrationAction.PasswordAction(it))
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
                        onAction(RegistrationAction.TogglePasswordVisibility)
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
            colors = CustomTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        Text("Repeat Password",
            modifier = Modifier.align(Alignment.Start))
        OutlinedTextField(
            value = uiState.passwordRepeat,
            onValueChange = {
                onAction(RegistrationAction.PasswordRepeatAction(it))
            },
            isError = uiState.passwordRepeatError!= null,
            supportingText = {
                uiState.passwordRepeatError?.let {
                    Text(it.asString())
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onAction(RegistrationAction.TogglePasswordVisibility)
                    }
                ) {
                    if (uiState.passwordRepeatVisible) {
                        Icon(painter = painterResource(R.drawable.eye_off), contentDescription = null)
                    }
                    else {
                        Icon(painter = painterResource(R.drawable.eye), contentDescription = null)
                    }
                }
            },
            visualTransformation =
                if (uiState.passwordRepeatVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            ,
            colors = CustomTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                onAction(RegistrationAction.Submit)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = LocalButtonShape.current,
            enabled = uiState.isRegistrationEnabled
        ) {
            Text("Create account")
        }

        TextButton(
            onClick = {
                onAction(RegistrationAction.Cancel)
            }
        ) {
            Text("Already have an account?")
        }


    }
}

@Preview
@Composable
private fun RegistrationFormPreview() {
    NoteMarkTheme {
        RegistrationForm(
            uiState = RegistrationUiState(
                password = "password",
                passwordVisible = true
            ),
            onAction = {}
        )
    }
}