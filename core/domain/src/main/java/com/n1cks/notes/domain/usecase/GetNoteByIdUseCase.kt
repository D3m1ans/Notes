package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.model.Note
import com.n1cks.notes.domain.repository.NoteRepository

class GetNoteByIdUseCase(
    private val repository: NoteRepository,
) {
    suspend operator fun invoke(id: Long): Note? = repository.getNoteById(id)
}