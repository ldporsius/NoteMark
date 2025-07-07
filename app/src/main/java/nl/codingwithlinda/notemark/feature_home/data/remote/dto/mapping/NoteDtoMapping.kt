package nl.codingwithlinda.notemark.feature_home.data.remote.dto.mapping

import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.FetchNotesResponseDto
import nl.codingwithlinda.notemark.feature_home.data.remote.dto.NoteResponseDto


fun NoteResponseDto.toDomain(): Note{
    return Note(
        id = id,
        title = title,
        content = content,
        dateCreated =  createdAt,
        dateLastUpdated = lastEditedAt
    )
}
fun FetchNotesResponseDto.toDomain(): List<Note>{

    return this.notes.map {
        it.toDomain()
    }
}