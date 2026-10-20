package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.model.Note
import com.n1cks.notes.domain.repository.FakeNoteRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ObserveNotesUseCaseTest {
    private val repository = FakeNoteRepository()
    private val observeNotes = ObserveNotesUseCase(repository)


    private fun note(title: String, updatedAt: Long, isPinned: Boolean = false) = Note(
        title = title,
        content = "",
        isPinned = isPinned,
        createdAt = updatedAt,
        updatedAt = updatedAt
    )

    @Test
    fun `pinned note goes first even if it is older`() = runBlocking {
        repository.saveNote(note("new", updatedAt = 2_000L))
        repository.saveNote(note("old pinned", updatedAt = 1_000L, isPinned = true))

        val result = observeNotes().first()

        assertEquals(listOf("old pinned", "new"), result.map { it.title })
    }

    @Test
    fun `unpinned notes are sorted newest first`() = runBlocking {
        repository.saveNote(note("first", updatedAt = 1_000L))
        repository.saveNote(note("third", updatedAt = 3_000L))
        repository.saveNote(note("second", updatedAt = 2_000L))

        val result = observeNotes().first()

        assertEquals(listOf("third", "second", "first"), result.map { it.title })
    }

    @Test
    fun `query filters by title or content ignoring case`() = runBlocking {
        repository.saveNote(Note(title = "Milk", content = "", createdAt = 1L, updatedAt = 1L))
        repository.saveNote(Note(title = "Things", content = "buy MILK", createdAt = 2L, updatedAt = 2L))
        repository.saveNote(Note(title = "Bread", content = "", createdAt = 3L, updatedAt = 3L))

        val result = observeNotes("Milk").first()

        assertEquals(listOf("Things", "Milk"), result.map { it.title })
    }
}