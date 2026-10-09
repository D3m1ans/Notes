package com.n1cks.notes.domain.model

data class Note(
    val id: Long = 0L,
    val title: String,
    val content: String,
    val isPinned: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
)
