package com.n1cks.features.notes.notelist.component

import com.arkivanov.decompose.ComponentContext
import com.n1cks.features.notes.extensions.componentScope
import com.n1cks.notes.domain.usecase.DeleteNoteUseCase
import com.n1cks.notes.domain.usecase.ObserveNotesUseCase
import com.n1cks.features.notes.notelist.state.NoteListUiState
import com.n1cks.notes.domain.model.Note
import com.n1cks.notes.domain.usecase.RestoreNoteUseCase
import com.n1cks.notes.domain.usecase.TogglePinNoteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class NoteListComponent(
    componentContext: ComponentContext,
    observeNotes: ObserveNotesUseCase,
    private val deleteNote: DeleteNoteUseCase,
    private val togglePinNote: TogglePinNoteUseCase,
    private val restoreNote: RestoreNoteUseCase,
    val onAddNote: () -> Unit,
    val onNoteClick: (Long) -> Unit,
) : ComponentContext by componentContext {

    private val scope = componentScope()
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val uiState: StateFlow<NoteListUiState> = _query
        .flatMapLatest { text -> observeNotes(text) }
        .map { notes -> NoteListUiState(notes = notes, isLoading = false) }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteListUiState(),
        )

    private val _deletedNotes = Channel<Note>(Channel.BUFFERED)
    val deleteNotes: Flow<Note> = _deletedNotes.receiveAsFlow()

    fun onQueryChange(value: String) {
        _query.value = value
    }

    fun onTogglePin(id: Long) {
        scope.launch { togglePinNote(id) }
    }

    fun onDeleteNote(id: Long) {
        val note = uiState.value.notes.firstOrNull { it.id == id } ?: return
        scope.launch {
            deleteNote(id)
            _deletedNotes.send(note)
        }
    }

    fun onUndoDelete(note: Note) {
        scope.launch { restoreNote(note) }
    }
}