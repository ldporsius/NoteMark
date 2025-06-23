package nl.codingwithlinda.notemark.core.navigation.dto

import kotlinx.serialization.Serializable

@Serializable
data class EditNoteDto(
    val id: String="",
    val title: String="",
    val content: String="",
    val dateCreated: String="",
)