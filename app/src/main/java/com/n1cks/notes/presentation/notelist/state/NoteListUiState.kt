package com.n1cks.notes.presentation.notelist.state

import com.n1cks.notes.domain.model.Note

data class NoteListUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = true
)
