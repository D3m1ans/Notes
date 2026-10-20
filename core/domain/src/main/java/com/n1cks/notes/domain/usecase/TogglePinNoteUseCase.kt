package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.repository.NoteRepository

class TogglePinNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Long) {
        val note = repository.getNoteById(id) ?: return
        repository.saveNote(note.copy(isPinned = !note.isPinned))
    }
}