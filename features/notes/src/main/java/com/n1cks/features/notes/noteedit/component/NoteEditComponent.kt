package com.n1cks.features.notes.noteedit.component

import com.arkivanov.decompose.ComponentContext
import com.n1cks.features.notes.extensions.componentScope
import com.n1cks.features.notes.noteedit.state.NoteEditUiState
import com.n1cks.notes.domain.model.InvalidNoteException
import com.n1cks.notes.domain.usecase.AddNoteUseCase
import com.n1cks.notes.domain.usecase.GetNoteByIdUseCase
import com.n1cks.notes.domain.usecase.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val NEW_NOTE_ID = 0L

class NoteEditComponent(
    componentContext: ComponentContext,
    private val noteId: Long,
    private val getNoteById: GetNoteByIdUseCase,
    private val addNote: AddNoteUseCase,
    private val updateNote: UpdateNoteUseCase,
    val onFinished: () -> Unit
) : ComponentContext by componentContext {

    private val scope = componentScope()
    private val isNewNote =
        noteId == NEW_NOTE_ID

    private val _uiState = MutableStateFlow(
        NoteEditUiState(
            isNewNote = isNewNote,
            isLoading = !isNewNote
        )
    )

    val uiState: StateFlow<NoteEditUiState> =
        _uiState.asStateFlow()

    init {
        if (!isNewNote) scope.launch { loadNote() }
    }

    private suspend fun loadNote() {
        val note = getNoteById(noteId)
        _uiState.update {
            if (note == null) it.copy(isLoading = false, errorMessage = "Note wasn't found")
            else it.copy(title = note.title, content = note.content, isLoading = false)
        }
    }

    fun onTitleChange(value: String) =
        _uiState.update { it.copy(title = value, errorMessage = null) }

    fun onContentChange(value: String) =
        _uiState.update { it.copy(content = value, errorMessage = null) }

    fun onSave() {
        val state = _uiState.value
        scope.launch {
            try {
                if (isNewNote) addNote(title = state.title, content = state.content)
                else updateNote(noteId, state.title, state.content)
                onFinished()
            } catch (e: InvalidNoteException) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }
}