package nl.codingwithlinda.notemark.core.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {

    data class DynamicText(val text: String): UiText
    data class StringResourceText(val resource:Int, val args: List<Any> = emptyList()): UiText

    @Composable
    fun asString(): String{
        return when(this){
            is DynamicText -> text
            is StringResourceText -> stringResource(id = resource,formatArgs = args.toTypedArray())
        }
    }
}