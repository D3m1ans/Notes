package com.n1cks.features.notes.notelist.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.n1cks.features.notes.notelist.component.NoteListComponent
import com.n1cks.features.notes.notelist.ui.content.NoteListContent

@Composable
fun NoteListScreen(component: NoteListComponent) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    NoteListContent(
        uiState = uiState,
        onAddNote = component.onAddNote,
        onNoteClick = component.onNoteClick,
        onDeleteNote = component::onDeleteNote,
    )
}