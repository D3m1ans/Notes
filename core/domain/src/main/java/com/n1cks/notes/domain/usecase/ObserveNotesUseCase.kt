package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.model.Note
import com.n1cks.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> =
        repository.observeNotes().map { notes ->
            notes.sortedWith(
                compareByDescending<Note> { it.isPinned }
                    .thenByDescending { it.updatedAt }
            )
        }
}