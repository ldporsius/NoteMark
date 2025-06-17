package nl.codingwithlinda.notemark.feature_home.data.remote.dto

/*"id":  String,
"title": "<title>" String),
"content": "<content>" String),
"createdAt": "<created at timestamp>" ISO 8601 String),
"lastEditedAt": "<last edited at timestamp>" ISO 8601 String)*/
data class NoteResponseDto(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val lastEditedAt: String
)
