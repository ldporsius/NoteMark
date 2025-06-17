package nl.codingwithlinda.notemark.core.domain.error

import nl.codingwithlinda.notemark.core.util.Error

enum class RemoteError(val code: Int): Error {
    UnknownError(code = -2)
}