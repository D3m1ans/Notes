package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.model.Note
import com.n1cks.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(query: String = ""): Flow<List<Note>> =
        repository.observeNotes().map { notes ->
            notes
                .filter { it.matches(query) }
                .sortedWith(
                compareByDescending<Note> { it.isPinned }
                    .thenByDescending { it.updatedAt }
            )
        }

    private fun Note.matches(query: String): Boolean {
        val text = query.trim()
        return title.contains(text, ignoreCase = true) || content.contains(text, ignoreCase = true)
    }
}