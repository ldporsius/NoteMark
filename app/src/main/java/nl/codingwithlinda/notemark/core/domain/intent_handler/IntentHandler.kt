package nl.codingwithlinda.notemark.core.domain.intent_handler

import kotlinx.coroutines.channels.Channel

interface IntentHandler<INTENT> {
    val handler: Channel<INTENT>
    fun sendIntent (intent: INTENT)
}