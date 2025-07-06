package nl.codingwithlinda.notemark.feature_settings.presentation.state

sealed interface SettingsAction {

    data object Logout : SettingsAction

}