package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.model.InvalidNoteException
import com.n1cks.notes.domain.model.Note
import com.n1cks.notes.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository,
    private val currentTimeMillis: () -> Long = System::currentTimeMillis
) {
    suspend operator fun invoke(title: String, content: String): Long {
        if (title.isBlank() && content.isBlank()) {
            throw InvalidNoteException("Заметка не может быть пустой")
        }
        val now = currentTimeMillis()
        val note = Note(
            title = title.trim(),
            content = content.trim(),
            createdAt = now,
            updatedAt = now
        )
        return repository.saveNote(note)
    }
}