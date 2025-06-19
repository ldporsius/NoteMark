package nl.codingwithlinda.notemark.core.util

import android.R.attr.resource
import android.R.attr.text
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {

    data class DynamicText(val text: String): UiText
    data class DynamicTextWithArgs(val text: String, val args: List<Any>): UiText
    data class StringResourceText(val resource:Int, val args: List<Any> = emptyList()): UiText

    fun asString(context: Context): String{
        return when(this){
            is DynamicText -> text
            is DynamicTextWithArgs -> StringBuilder()
                .append(text)
                .append(args.joinToString(";"))
                .toString()
            is StringResourceText -> context.getString(resource, *args.toTypedArray())
        }
    }
    @Composable
    fun asString(): String{
        return when(this){
            is DynamicText -> text
            is DynamicTextWithArgs -> StringBuilder()
                .append(text)
                .append(args.joinToString(";"))
                .toString()
            is StringResourceText -> stringResource(id = resource,formatArgs = args.toTypedArray())
        }
    }
}