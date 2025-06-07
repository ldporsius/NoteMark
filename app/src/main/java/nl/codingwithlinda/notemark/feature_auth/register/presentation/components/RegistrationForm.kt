package nl.codingwithlinda.notemark.feature_auth.register.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import nl.codingwithlinda.notemark.R
import nl.codingwithlinda.notemark.core.util.UiText
import nl.codingwithlinda.notemark.design_system.ui.theme.LocalButtonShape
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.customTextFieldColors
import nl.codingwithlinda.notemark.design_system.ui.theme.onPrimary
import nl.codingwithlinda.notemark.design_system.ui.theme.primary
import nl.codingwithlinda.notemark.feature_auth.register.presentation.state.RegistrationAction
import nl.codingwithlinda.notemark.feature_auth.register.presentation.state.RegistrationUiState
import nl.codingwithlinda.notemark.feature_auth.register.presentation.state.RegistrationUiState.Companion.FocusTag

@Composable
fun RegistrationForm(
    uiState: RegistrationUiState,
    onAction: (RegistrationAction) -> Unit,
    modifier: Modifier = Modifier) {

    val focusManager = LocalFocusManager.current
    //val focusRequester = remember { FocusRequester() }

    var currentFocus: FocusTag? by remember { mutableStateOf(null) }

    @Composable
    fun focusModifier(
        onFocusedChanged: (focused: Boolean) -> Unit,
    ) = Modifier

        .onFocusChanged {
            onFocusedChanged(it.isFocused)
        }


    fun shouldShowError(myFocusTag: FocusTag, myError: UiText?): Boolean{
        val hasFocus = currentFocus == myFocusTag
        return myError != null && !hasFocus
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Username",
            modifier = Modifier.align(Alignment.Start))

        OutlinedTextField(
            value = uiState.username,
            onValueChange = {
                onAction(RegistrationAction.UserNameAction(it))
            },
            isError = shouldShowError(FocusTag.USERNAME, uiState.usernameError),
            supportingText =  {
                val text = uiState.userNameSupportingText(currentFocus == FocusTag.USERNAME)
                text?.let {
                    Text(it.asString())
                }
            },
            label = {  },
            singleLine = true,
            colors = customTextFieldColors(),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .then(focusModifier {
                    if (it) {
                        currentFocus = FocusTag.USERNAME
                    }
                    else {
                        currentFocus = null
                    }
                })
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
            isError = shouldShowError(FocusTag.EMAIL, uiState.emailError),
            supportingText = {
                uiState.emailSupportingText(currentFocus == FocusTag.EMAIL)?.let {
                    Text(it.asString())
                }
            },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            colors = customTextFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .then(focusModifier {
                    if (it) {
                        currentFocus = FocusTag.EMAIL
                    }
                    else {
                        currentFocus = null
                    }
                }
                )
        )

        Text("Password",
            modifier = Modifier.align(Alignment.Start))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = {
                onAction(RegistrationAction.PasswordAction(it))
            },
            isError = shouldShowError(FocusTag.PASSWORD, uiState.passwordError),
            supportingText = {
                uiState.passwordSupportingText(currentFocus == FocusTag.PASSWORD)?.let {
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
            singleLine = true,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            colors = customTextFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .then(focusModifier {
                    if (it) {
                        currentFocus = FocusTag.PASSWORD
                    }
                    else {
                        currentFocus = null
                    }
                }
                )

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
                        onAction(RegistrationAction.TogglePasswordRepeatVisibility)
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
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone  = {
                    focusManager.clearFocus()
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            colors = customTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        when (uiState.isLoading) {
            true -> CircularProgressIndicator()
            false -> {
                Button(
                    onClick = {
                        if (uiState.isRegistrationEnabled) {
                            onAction(RegistrationAction.Submit)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = LocalButtonShape.current,
                    enabled = uiState.isRegistrationEnabled,
                    colors = ButtonColors(
                        containerColor = primary,
                        contentColor = onPrimary,
                        disabledContainerColor = primary.copy(.5f),
                        disabledContentColor = onPrimary.copy(.5f)
                    )
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