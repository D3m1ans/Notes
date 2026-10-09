package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository,
) {
    suspend operator fun invoke(id: Long) = repository.deleteNote(id)
}