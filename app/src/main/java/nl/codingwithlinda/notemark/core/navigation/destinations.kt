package nl.codingwithlinda.notemark.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import nl.codingwithlinda.notemark.core.navigation.dto.EditNoteDto

@Serializable
data object HomeDestination: NavKey

sealed class NoteDestination: NavKey {
    @Serializable
    data object NoteListDestination : NoteDestination()
    @Serializable
    data class NoteDetailDestination(val noteDto: EditNoteDto) : NoteDestination()
}

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