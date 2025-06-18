package nl.codingwithlinda.notemark.feature_home.presentation.util

fun userNameAvatar(userName: String): String {
    val caps = userName.uppercase()
    val nameParts = caps.split(" ")

    return when (nameParts.size) {
        0 -> ""
        1 -> nameParts[0].take(2)
        else -> nameParts.first().take(1) + nameParts.last().take(1)
    }
}