package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.model.Note
import com.n1cks.notes.domain.repository.NoteRepository

class RestoreNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.saveNote(note)
}