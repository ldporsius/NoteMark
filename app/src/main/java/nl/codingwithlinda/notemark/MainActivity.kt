package nl.codingwithlinda.notemark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import nl.codingwithlinda.notemark.core.navigation.AuthRootDestination
import nl.codingwithlinda.notemark.core.navigation.HomeDestination
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.feature_auth.core.presentation.AuthRoot
import nl.codingwithlinda.notemark.feature_home.presentation.HomeRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen()

        setContent {
            NoteMarkTheme {

                    val backstack = rememberNavBackStack(
                        AuthRootDestination
                    )
                    NavDisplay(backstack){route ->
                        when(route) {
                            is HomeDestination -> {
                                NavEntry(route) {
                                  HomeRoot()
                                }
                            }
                            is AuthRootDestination -> {

                                NavEntry(route) {
                                   AuthRoot(
                                       navigateBack = {
                                           backstack.retainAll(
                                               listOf(AuthRootDestination)
                                           )
                                       },
                                       navigateHome = {
                                           backstack.retainAll(
                                               listOf(AuthRootDestination)
                                           )
                                           backstack.add(HomeDestination)
                                       },
                                       modifier = Modifier
                                   )
                                }
                            }

                            else -> {
                                NavEntry(route) {
                                    Text(text = "Unknown route")
                                }
                            }
                        }
                    }


            }
        }
    }
}

