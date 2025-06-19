package nl.codingwithlinda.notemark.feature_home.data.remote.dto

import kotlinx.serialization.Serializable

/*{
"notes": [
{
    "id": "<id>" UUID String),
    "title": "<title>" String),
    "content": "<content>" String),
    "createdAt": "<created at timestamp>" ISO 8601 String),NoteMark API Milestone #23
    "lastEditedAt": "<last edited at timestamp>" ISO 8601 String)
},
{
    "id": "<id>" UUID String),
    "title": "<title>" String),
    "content": "<content>" String),
    "createdAt": "<created at timestamp>" ISO 8601 String),
    "lastEditedAt": "<last edited at timestamp>" ISO 8601 String)
}
],
"total": "<total number of notes>" Integer)
}*/
@Serializable
data class FetchNotesResponseDto(
    val notes: List<NoteResponseDto>,
    val total: Int
)
