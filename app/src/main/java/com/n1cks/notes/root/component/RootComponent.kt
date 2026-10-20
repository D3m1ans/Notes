package com.n1cks.notes.root.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.n1cks.features.notes.noteedit.component.NEW_NOTE_ID
import com.n1cks.features.notes.noteedit.component.NoteEditComponent
import com.n1cks.features.notes.notelist.component.NoteListComponent
import com.n1cks.notes.domain.usecase.AddNoteUseCase
import com.n1cks.notes.domain.usecase.DeleteNoteUseCase
import com.n1cks.notes.domain.usecase.GetNoteByIdUseCase
import com.n1cks.notes.domain.usecase.ObserveNotesUseCase
import com.n1cks.notes.domain.usecase.RestoreNoteUseCase
import com.n1cks.notes.domain.usecase.TogglePinNoteUseCase
import com.n1cks.notes.domain.usecase.UpdateNoteUseCase
import com.n1cks.notes.root.child.Child
import com.n1cks.notes.root.config.RootConfig

@OptIn(DelicateDecomposeApi::class)
class RootComponent(
    componentContext: ComponentContext,
    private val observeNotes: ObserveNotesUseCase,
    private val deleteNote: DeleteNoteUseCase,
    private val getNoteById: GetNoteByIdUseCase,
    private val addNote: AddNoteUseCase,
    private val updateNote: UpdateNoteUseCase,
    private val togglePinNote: TogglePinNoteUseCase,
    private val restoreNote: RestoreNoteUseCase,
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<RootConfig>()

    val childStack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = RootConfig.serializer(),
        initialConfiguration = RootConfig.NoteList,
        handleBackButton = true,
        childFactory = ::createChild,
    )

    private fun createChild(config: RootConfig, context: ComponentContext): Child =
        when (config) {
            RootConfig.NoteList -> Child.NoteList(
                NoteListComponent(
                    componentContext = context,
                    observeNotes = observeNotes,
                    deleteNote = deleteNote,
                    togglePinNote = togglePinNote,
                    restoreNote = restoreNote,
                    onAddNote = { navigation.push(RootConfig.NoteEdit(NEW_NOTE_ID)) },
                    onNoteClick = { id -> navigation.push(RootConfig.NoteEdit(id)) }
                )
            )

            is RootConfig.NoteEdit -> Child.NoteEdit(
                NoteEditComponent(
                    componentContext = context,
                    noteId = config.noteId,
                    getNoteById = getNoteById,
                    addNote = addNote,
                    updateNote = updateNote,
                    onFinished = { navigation.pop() }
                )
            )
        }
}