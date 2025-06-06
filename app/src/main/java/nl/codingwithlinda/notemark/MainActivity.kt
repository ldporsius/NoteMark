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
                                   Box(Modifier.fillMaxSize(),
                                       contentAlignment = Alignment.Center){
                                       Text(text = "This is the home screen")
                                   }
                                }
                            }
                            is AuthRootDestination -> {

                                NavEntry(route) {
                                   AuthRoot(
                                       navigateBack = {
                                           backstack.retainAll(
                                               listOf(HomeDestination)
                                           )
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

