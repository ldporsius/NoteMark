package nl.codingwithlinda.notemark.feature_home.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateNoteRequestDto(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val lastEditedAt: String
)
