package com.n1cks.core.data.mapper

import com.n1cks.core.data.local.entity.NoteEntity
import com.n1cks.notes.domain.model.Note

fun NoteEntity.toDomain(): Note = Note(
    id = id,
    title = title,
    content = content,
    isPinned = isPinned,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Note.toEntity(): NoteEntity = NoteEntity(
    id = id,
    title = title,
    content = content,
    isPinned = isPinned,
    createdAt = createdAt,
    updatedAt = updatedAt
)