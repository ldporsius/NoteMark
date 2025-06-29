package nl.codingwithlinda.notemark

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.coroutines.flow.firstOrNull
import nl.codingwithlinda.notemark.app.NoteMarkApplication
import nl.codingwithlinda.notemark.core.data.auth.session.KtorSessionManager
import nl.codingwithlinda.notemark.core.di.AndroidAppModule
import nl.codingwithlinda.notemark.core.navigation.AuthRootDestination
import nl.codingwithlinda.notemark.core.navigation.HomeDestination
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.surface
import nl.codingwithlinda.notemark.feature_auth.core.presentation.AuthRoot
import nl.codingwithlinda.notemark.feature_home.presentation.HomeRoot
import nl.codingwithlinda.notemark.navigation.NavigationRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mainViewModel by viewModels<MainViewModel>(
            factoryProducer = {
                viewModelFactory {
                    initializer {
                        MainViewModel(
                            sessionManager = KtorSessionManager(application)
                        )
                    }
                }
            }
        )
        installSplashScreen().setKeepOnScreenCondition(
            condition = {
                mainViewModel.isLoading
            }
        )


        val application = this.application as Application

        setContent {
            NoteMarkTheme {
                val sessionManager = remember {
                    KtorSessionManager(application)
                }

                val noteRepository = remember {
                    NoteMarkApplication.appModule.noteRepository
                }
                Box(modifier = Modifier.fillMaxSize().background(surface)) {
                    NavigationRoot(
                        sessionManager = sessionManager,
                        noteRepository = noteRepository
                    )
                }
            }
        }
    }
}

