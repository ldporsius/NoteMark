package nl.codingwithlinda.notemark.core.presentation

import nl.codingwithlinda.notemark.core.domain.error.ConnectivityError
import nl.codingwithlinda.notemark.core.domain.error.DataError
import nl.codingwithlinda.notemark.core.domain.error.LocalError
import nl.codingwithlinda.notemark.core.domain.error.RemoteError
import nl.codingwithlinda.notemark.core.util.Error
import nl.codingwithlinda.notemark.core.util.UiText
import nl.codingwithlinda.notemark.core.util.UiText.*


fun Error.toUiText(): UiText{
    return when(this){
        is DataError -> this.toUiText()
        is LocalError -> this.toUiText()
        is RemoteError -> this.toUiText()
        else -> DynamicText("Unknown error")
    }
}
fun DataError.toUiText(): UiText{
   return when(this){
        is DataError.LocalDataError -> this.error.toUiText()
        is DataError.RemoteDataError -> {
            val args = this.msg?.let{listOf(this.msg)} ?: emptyList()
            DynamicTextWithArgs(
                text = this.error.mapToString(),
                args = args
            )
        }
    }
}

fun LocalError.toUiText(): UiText{
    return when(this){
        LocalError.DISK_FULL -> UiText.DynamicText("Disk full")
        LocalError.NOT_FOUND -> UiText.DynamicText("Not found")
        LocalError.UnknownError -> UiText.DynamicText("Something went wrong while accessing the database")

    }
}

fun RemoteError.mapToString(): String{
    return when(this){
        RemoteError.BadRequestError -> "Bad request"
        RemoteError.InternalServerError -> "Internal server error"
        RemoteError.MethodNotAllowedError -> "Method not allowed"
        RemoteError.ToManyRequestsError -> "Too many requests"
        RemoteError.UnknownError -> "Unknown error"
    }
}
fun RemoteError.toUiText(): UiText {
    return when (this) {
        RemoteError.BadRequestError -> DynamicText("Bad request")
        RemoteError.InternalServerError -> DynamicText("Internal server error")
        RemoteError.MethodNotAllowedError -> DynamicText("Method not allowed")
        RemoteError.ToManyRequestsError -> DynamicText("Too many requests")
        RemoteError.UnknownError -> DynamicText("Unknown error")
    }
}

fun ConnectivityError.toUiText(): UiText {
    return when(this){
        ConnectivityError.NoConnectivityError -> DynamicText("Check your internet connection")
        ConnectivityError.UnknownError -> DynamicText("Unknown error")

    }
}