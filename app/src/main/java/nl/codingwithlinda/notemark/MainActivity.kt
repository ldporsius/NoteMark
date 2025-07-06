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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.codingwithlinda.notemark.app.NoteMarkApplication
import nl.codingwithlinda.notemark.core.data.auth.session.KtorSessionManager
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.surface
import nl.codingwithlinda.notemark.core.navigation.MainNavGraph

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
                    MainNavGraph(
                        sessionManager = sessionManager,
                        noteRepository = noteRepository
                    )
                }
            }
        }
    }
}

