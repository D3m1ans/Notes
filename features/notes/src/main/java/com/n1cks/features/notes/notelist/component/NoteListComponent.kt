package com.n1cks.features.notes.notelist.component

import com.arkivanov.decompose.ComponentContext
import com.n1cks.features.notes.extensions.componentScope
import com.n1cks.notes.domain.usecase.DeleteNoteUseCase
import com.n1cks.notes.domain.usecase.ObserveNotesUseCase
import com.n1cks.features.notes.notelist.state.NoteListUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteListComponent(
    componentContext: ComponentContext,
    observeNotes: ObserveNotesUseCase,
    private val deleteNote: DeleteNoteUseCase,
    val onAddNote: () -> Unit,
    val onNoteClick: (Long) -> Unit,
) : ComponentContext by componentContext {

    private val scope = componentScope()

    val uiState: StateFlow<NoteListUiState> = observeNotes()
        .map { notes -> NoteListUiState(notes = notes, isLoading = false) }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteListUiState(),
        )

    fun onDeleteNote(id: Long) {
        scope.launch { deleteNote(id) }
    }
}