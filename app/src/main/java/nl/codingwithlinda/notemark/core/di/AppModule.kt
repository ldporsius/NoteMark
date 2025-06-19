package nl.codingwithlinda.notemark.core.di

import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository

interface AppModule {
    val noteRepository: NoteRepository
}