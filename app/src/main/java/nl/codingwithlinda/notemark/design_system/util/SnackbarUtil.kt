package nl.codingwithlinda.notemark.design_system.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import nl.codingwithlinda.notemark.core.util.UiText


data class SnackbarEvent(
    val message: UiText,
    val action: SnackbarAction? = null,
)

data class SnackbarAction(
    val name: UiText,
    val action: () -> Unit,
)


object SnackBarController{
    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent){
        _events.send(event)
    }
}