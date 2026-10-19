package com.n1cks.notes.root.config

import kotlinx.serialization.Serializable

@Serializable
sealed class RootConfig {
    @Serializable
    data object NoteList : RootConfig()
    @Serializable
    data class NoteEdit(val noteId: Long) : RootConfig()
}