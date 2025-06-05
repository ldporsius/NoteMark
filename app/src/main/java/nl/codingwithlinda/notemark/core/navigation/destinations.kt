package nl.codingwithlinda.notemark.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object HomeDestination: NavKey

@Serializable
data object AuthRootDestination: NavKey

sealed class AuthDestination: NavKey {
    @Serializable
    data object WelcomeDestination : AuthDestination()

    @Serializable
    data object LoginDestination : AuthDestination()

    @Serializable
    data object RegisterDestination : AuthDestination()
}