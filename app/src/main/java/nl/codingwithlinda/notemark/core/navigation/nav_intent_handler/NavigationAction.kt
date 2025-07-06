package nl.codingwithlinda.notemark.core.navigation.nav_intent_handler

import androidx.navigation.NavOptionsBuilder
import androidx.navigation3.runtime.NavKey

sealed interface NavigationAction {

    data class Navigate(
        val destination: NavKey,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ): NavigationAction

    data object NavigateUp: NavigationAction

    data object PopBackStack: NavigationAction

    data class PopupTo(val destination: NavKey, val inclusive: Boolean = false): NavigationAction
}