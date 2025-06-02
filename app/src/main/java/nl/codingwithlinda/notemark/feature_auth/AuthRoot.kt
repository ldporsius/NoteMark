package nl.codingwithlinda.notemark.feature_auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import nl.codingwithlinda.notemark.core.navigation.AuthDestination

@Composable
fun AuthRoot(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text("This is the auth root", modifier = modifier)

        val backstackAuth:NavBackStack = rememberNavBackStack<AuthDestination>(
            AuthDestination.LoginDestination
        )
        NavDisplay(
            backStack = backstackAuth,
            entryProvider = entryProvider {
                entry(AuthDestination.LoginDestination){
                    Column {
                        Text(text = "This is the Login screen")
                        Button(
                            onClick = onLoginSuccess
                        ) {
                            Text("Login successfully")
                        }
                        Button(
                            onClick = {
                                backstackAuth.add(AuthDestination.RegisterDestination)
                            }
                        ) {
                            Text("Register")
                        }
                    }
                }

                entry(AuthDestination.RegisterDestination){
                    Column {
                        Text(text = "Here is the register screen")
                        Button(
                            onClick = {
                                backstackAuth.removeLastOrNull()
                            }
                        ) {
                            Text("Back")
                        }
                    }
                }
            }
        )
    }
}