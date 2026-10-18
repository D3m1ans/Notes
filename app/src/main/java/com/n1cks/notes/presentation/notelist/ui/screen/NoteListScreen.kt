package com.n1cks.notes.presentation.notelist.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.n1cks.notes.presentation.notelist.ui.content.NoteListContent
import com.n1cks.notes.presentation.notelist.viewModel.NoteListViewModel

@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NoteListContent(
        uiState = uiState,
        onAddSampleNote = viewModel::onAddSampleNote,
        onDeleteNote = viewModel::onDeleteNote
    )
}

