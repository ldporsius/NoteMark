package nl.codingwithlinda.notemark.feature_auth.presentation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateBounds
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import nl.codingwithlinda.notemark.core.navigation.AuthDestination
import nl.codingwithlinda.notemark.feature_auth.presentation.components.LoginHeader

@OptIn(ExperimentalSharedTransitionApi::class)
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
        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

        println("AUTH ROOT WINDOW SIZE CLASS: $windowSizeClass")

        NavDisplay(
            backStack = backstackAuth,
            entryProvider = entryProvider {
                entry(AuthDestination.LoginDestination){

                    Column(
                        modifier = Modifier
                    ) {
                        LookaheadScope {
                            LoginHeader(modifier = Modifier
                                .animateBounds(this@LookaheadScope)
                            )
                            Button(
                                modifier = Modifier
                                    //.width(150.dp)
                                    .height(50.dp)
                                ,
                                onClick = {
                                    backstackAuth.add(AuthDestination.RegisterDestination)
                                }
                            ) {
                                BasicText(
                                    text = "test a very long text",
                                    style = MaterialTheme.typography.headlineLarge,
                                    autoSize = TextAutoSize.StepBased()
                                )
                            }
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