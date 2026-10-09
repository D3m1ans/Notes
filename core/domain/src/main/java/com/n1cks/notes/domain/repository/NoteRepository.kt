package com.n1cks.notes.domain.repository

import com.n1cks.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun observeNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Long): Note?
    suspend fun saveNote(note: Note): Long
    suspend fun deleteNote(id: Long)
}