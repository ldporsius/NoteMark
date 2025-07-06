package nl.codingwithlinda.notemark.core.navigation.nav_intent_handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import nl.codingwithlinda.notemark.core.domain.intent_handler.IntentHandler

class NavigationIntentHandler(
    private val scope: CoroutineScope
): IntentHandler<NavigationAction> {

    override val handler = Channel<NavigationAction>()
    override fun sendIntent(intent: NavigationAction) {
        scope.launch {
            handler.send(intent)
        }
    }
}