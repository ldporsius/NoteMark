package nl.codingwithlinda.notemark.feature_home.data.remote.dto

import kotlinx.serialization.Serializable


//Description: Retrieve a paginated list of notes. Supports pagination via page and
//size query parameters.
//Query parameters:
//page  The page number to retrieve (starting from 0. If page is set to 1, all notes
//are returned in a single response.
//size  The number of notes to retrieve per page. Only relevant if page is 0 or higher.

@Serializable
data class FetchNotesRequestDto(
    val page: Int = -1,
    val size: Int = 0
)
