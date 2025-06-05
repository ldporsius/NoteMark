package nl.codingwithlinda.notemark.core.util

sealed interface Result<out D, out E: Error> {
    data class Success<out D, out E: nl.codingwithlinda.notemark.core.util.Error>(val data: D): Result<D, E>
    data class Error<out D, out E: nl.codingwithlinda.notemark.core.util.Error>(val error: E): Result<D, E>
}