package nl.codingwithlinda.notemark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import nl.codingwithlinda.notemark.core.navigation.AuthRootDestination
import nl.codingwithlinda.notemark.core.navigation.HomeDestination
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.primary
import nl.codingwithlinda.notemark.feature_auth.presentation.AuthRoot
import nl.codingwithlinda.notemark.feature_home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteMarkTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = primary
                ) { innerPadding ->
                    val ip = innerPadding
                    val backstack = rememberNavBackStack(
                        HomeDestination
                    )
                    NavDisplay(backstack){route ->
                        when(route) {
                            is HomeDestination -> {
                                NavEntry(route) {
                                    HomeScreen(
                                        onLoginClick = {
                                            backstack.add(AuthRootDestination)
                                        },
                                        modifier = Modifier.padding(innerPadding)
                                    )
                                }
                            }
                            is AuthRootDestination -> {

                                NavEntry(route) {
                                   AuthRoot(
                                       onLoginSuccess = {
                                           backstack.retainAll(
                                               listOf(HomeDestination)
                                           )
                                       },
                                       modifier = Modifier.padding(innerPadding)
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
}

