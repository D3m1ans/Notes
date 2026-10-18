package com.n1cks.core.data.factory

import android.content.Context
import com.n1cks.core.data.local.dataBase.createNotesDatabase
import com.n1cks.core.data.repository.RoomNoteRepository
import com.n1cks.notes.domain.repository.NoteRepository

fun createNoteRepository(context: Context): NoteRepository =
    RoomNoteRepository(createNotesDatabase(context).noteDao())