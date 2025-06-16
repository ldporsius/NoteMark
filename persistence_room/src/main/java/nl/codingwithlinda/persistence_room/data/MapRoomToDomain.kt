package nl.codingwithlinda.persistence_room.data

import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.persistence_room.model.NoteEntity

fun NoteEntity.toDomain(): Note{
    return Note(
        id = this.id,
        title = this.title,
        content = this.content,
        dateCreated = this.dateCreated
    )
}

fun Note.toRoom(): NoteEntity{
    return NoteEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        dateCreated = this.dateCreated
    )
}