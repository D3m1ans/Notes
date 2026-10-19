package com.n1cks.features.notes.noteedit.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.n1cks.features.notes.noteedit.component.NoteEditComponent
import com.n1cks.features.notes.noteedit.ui.content.NoteEditContent

@Composable
fun NoteEditScreen(component: NoteEditComponent) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    NoteEditContent(
        uiState = uiState,
        onTitleChange = component::onTitleChange,
        onContentChange = component::onContentChange,
        onSave = component::onSave,
        onBack = component.onFinished,
    )
}