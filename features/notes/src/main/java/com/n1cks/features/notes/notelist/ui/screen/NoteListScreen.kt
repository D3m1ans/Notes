package com.n1cks.features.notes.notelist.ui.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.n1cks.features.notes.notelist.component.NoteListComponent
import com.n1cks.features.notes.notelist.ui.content.NoteListContent

@Composable
fun NoteListScreen(component: NoteListComponent) {
    val uiState by component.uiState.collectAsStateWithLifecycle()
    val query by component.query.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }


    LaunchedEffect(component) {
        component.deleteNotes.collect { note ->
            val result = snackBarHostState.showSnackbar(
                message = "Note deleted",
                actionLabel = "Cancel"
            )
            if (result == SnackbarResult.ActionPerformed) component.onUndoDelete(note)
        }
    }

    NoteListContent(
        uiState = uiState,
        query = query,
        snackBarHostState = snackBarHostState,
        onQueryChange = component::onQueryChange,
        onAddNote = component.onAddNote,
        onNoteClick = component.onNoteClick,
        onTogglePin = component::onTogglePin,
        onDeleteNote = component::onDeleteNote,
    )
}