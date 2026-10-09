package com.n1cks.notes.domain.repository

import com.n1cks.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeNoteRepository : NoteRepository {

    private val notes = MutableStateFlow<List<Note>>(emptyList())
    private var nextId = 1L

    override fun observeNotes(): Flow<List<Note>> = notes

    override suspend fun getNoteById(id: Long): Note? = notes.value.find { it.id == id }

    override suspend fun saveNote(note: Note): Long {
        val id = if (note.id == 0L) nextId++ else note.id
        notes.value = notes.value.filter { it.id != id } + note.copy(id = id)
        return id
    }

    override suspend fun deleteNote(id: Long) {
        notes.value = notes.value.filter { it.id != id }
    }
}