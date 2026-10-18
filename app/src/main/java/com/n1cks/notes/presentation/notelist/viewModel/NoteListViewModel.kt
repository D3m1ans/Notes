package com.n1cks.notes.presentation.notelist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n1cks.notes.domain.usecase.AddNoteUseCase
import com.n1cks.notes.domain.usecase.DeleteNoteUseCase
import com.n1cks.notes.domain.usecase.ObserveNotesUseCase
import com.n1cks.notes.presentation.notelist.state.NoteListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    observeNotes: ObserveNotesUseCase,
    private val deleteNote: DeleteNoteUseCase,
    private val addNote: AddNoteUseCase
): ViewModel() {

    val uiState: StateFlow<NoteListUiState> = observeNotes()
        .map { notes -> NoteListUiState(notes = notes, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteListUiState(),
        )

    fun onDeleteNote(id: Long) {
        viewModelScope.launch { deleteNote(id) }
    }

    fun onAddSampleNote() {
        viewModelScope.launch {
            addNote("Note ${System.currentTimeMillis() % 10_000}", "Test text")
        }
    }
}