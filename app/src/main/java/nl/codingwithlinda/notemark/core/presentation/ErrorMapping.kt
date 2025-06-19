package nl.codingwithlinda.notemark.core.presentation

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
        is DataError.RemoteDataError -> this.error.toUiText()
    }
}

fun LocalError.toUiText(): UiText{
    return when(this){
        LocalError.DISK_FULL -> UiText.DynamicText("Disk full")
        LocalError.NOT_FOUND -> UiText.DynamicText("Not found")
        LocalError.UnknownError -> UiText.DynamicText("Unknown error")

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