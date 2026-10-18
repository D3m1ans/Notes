package com.n1cks.core.data.repository

import com.n1cks.core.data.local.dao.NoteDao
import com.n1cks.core.data.mapper.toDomain
import com.n1cks.core.data.mapper.toEntity
import com.n1cks.notes.domain.model.Note
import com.n1cks.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomNoteRepository(
    private val dao: NoteDao
) : NoteRepository {
    override fun observeNotes(): Flow<List<Note>> =
        dao.observeAll().map { entities ->
            entities.map { it.toDomain() }
        }


    override suspend fun getNoteById(id: Long): Note? =
        dao.getById(id)?.toDomain()

    override suspend fun saveNote(note: Note): Long {
        val rowId = dao.upsert(note.toEntity())
        return if (note.id == 0L) rowId else note.id
    }

    override suspend fun deleteNote(id: Long) = dao.deleteById(id)
}