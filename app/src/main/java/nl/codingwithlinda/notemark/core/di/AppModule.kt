package nl.codingwithlinda.notemark.core.di

import nl.codingwithlinda.notemark.core.data.auth.register.RegisterService
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository

interface AppModule {
    val noteRepository: NoteRepository
    val registerService: RegisterService
}