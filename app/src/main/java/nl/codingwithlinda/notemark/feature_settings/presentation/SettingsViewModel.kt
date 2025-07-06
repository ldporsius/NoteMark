package nl.codingwithlinda.notemark.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.domain.auth.SessionManager
import nl.codingwithlinda.notemark.core.navigation.AuthRootDestination
import nl.codingwithlinda.notemark.core.navigation.nav_intent_handler.NavigationAction
import nl.codingwithlinda.notemark.core.navigation.nav_intent_handler.NavigationIntentHandler
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.core.util.SnackBarController
import nl.codingwithlinda.notemark.core.util.SnackbarEvent
import nl.codingwithlinda.notemark.core.util.UiText
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.feature_settings.presentation.state.SettingsAction

class SettingsViewModel(
    private val noteRepo : NoteRepository,
    private val sessionManager: SessionManager,
    private val navigator: NavigationIntentHandler
): ViewModel() {

    fun handleAction(action: SettingsAction) {
        when(action) {
            SettingsAction.Logout -> {

                viewModelScope.launch(NonCancellable) {
                   val logoutRes = sessionManager.logout()

                    when(logoutRes){
                        is Result.Error ->{
                            //warn user of error
                            println("ERROR LOGGING OUT: ${logoutRes.error}")
                            SnackBarController.sendEvent(SnackbarEvent(
                                message = UiText.DynamicText("Error logging out")
                            ))
                        }
                        is Result.Success ->{
                            //delete all notes in local database
                            noteRepo.deleteAllNotes()
                            // redirect to login
                            val navAction = NavigationAction.Navigate(AuthRootDestination){
                                popUpTo(AuthRootDestination){
                                    inclusive = false
                                }
                            }
                            navigator.sendIntent(navAction)
                        }
                    }
                }
            }
        }

    }
}