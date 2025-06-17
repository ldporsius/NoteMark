package nl.codingwithlinda.persistence_room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val content: String,
    val dateCreated: String,
    val dateLastUpdated: String
)
