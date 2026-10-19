package com.n1cks.notes.root.child

import com.n1cks.features.notes.noteedit.component.NoteEditComponent
import com.n1cks.features.notes.notelist.component.NoteListComponent

sealed class Child {
    class NoteList(val component: NoteListComponent) : Child()
    class NoteEdit(val component: NoteEditComponent) : Child()
}