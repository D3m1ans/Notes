package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.model.InvalidNoteException
import com.n1cks.notes.domain.repository.NoteRepository

class UpdateNoteUseCase(
    private val repository: NoteRepository,
    private val currentTimeMillis: () -> Long = System::currentTimeMillis
) {
    suspend operator fun invoke(id: Long, title: String, content: String) {
        if (title.isBlank() && content.isBlank()) {
            throw InvalidNoteException("Note cannot be empty")
        }

        val existing = repository.getNoteById(id)
            ?: throw InvalidNoteException("Note not found")

        repository.saveNote(
            existing.copy(
                title = title.trim(),
                content = content.trim(),
                updatedAt = currentTimeMillis()
            )
        )
    }
}