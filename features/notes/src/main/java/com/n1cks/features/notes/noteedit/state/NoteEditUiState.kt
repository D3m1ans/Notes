package com.n1cks.features.notes.noteedit.state

data class NoteEditUiState(
    val title: String = "",
    val content: String = "",
    val isNewNote: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
