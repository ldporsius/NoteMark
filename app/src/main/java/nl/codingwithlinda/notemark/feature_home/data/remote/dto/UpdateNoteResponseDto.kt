package nl.codingwithlinda.notemark.feature_home.data.remote.dto

import kotlinx.serialization.Serializable

/*"id":  String,
"title": "<title>" String),
"content": "<content>" String),
"createdAt": "<created at timestamp>" ISO 8601 String),
"lastEditedAt": "<last edited at timestamp>" ISO 8601 String)*/


@Serializable
data class UpdateNoteResponseDto(
    val id: String,
    val title: String? = null,
    val content: String? = null,
    val createdAt: String? = null,
    val lastEditedAt: String? = null
)
