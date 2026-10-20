package com.n1cks.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.retainedComponent
import com.n1cks.notes.domain.usecase.AddNoteUseCase
import com.n1cks.notes.domain.usecase.DeleteNoteUseCase
import com.n1cks.notes.domain.usecase.GetNoteByIdUseCase
import com.n1cks.notes.domain.usecase.ObserveNotesUseCase
import com.n1cks.notes.domain.usecase.RestoreNoteUseCase
import com.n1cks.notes.domain.usecase.TogglePinNoteUseCase
import com.n1cks.notes.domain.usecase.UpdateNoteUseCase
import com.n1cks.notes.root.component.RootComponent
import com.n1cks.notes.root.ui.RootContent
import com.n1cks.notes.ui.theme.NotesTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var observeNotes: ObserveNotesUseCase

    @Inject
    lateinit var deleteNote: DeleteNoteUseCase

    @Inject
    lateinit var getNoteById: GetNoteByIdUseCase

    @Inject
    lateinit var addNote: AddNoteUseCase

    @Inject
    lateinit var updateNote: UpdateNoteUseCase

    @Inject
    lateinit var togglePinNote: TogglePinNoteUseCase

    @Inject
    lateinit var restoreNote: RestoreNoteUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val root = retainedComponent { context ->
            RootComponent(
                context,
                observeNotes,
                deleteNote,
                getNoteById,
                addNote,
                updateNote,
                togglePinNote,
                restoreNote
            )
        }

        setContent {
            NotesTheme { RootContent(root) }
        }
    }
}