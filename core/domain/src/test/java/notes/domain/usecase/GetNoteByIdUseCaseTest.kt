package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.model.Note
import com.n1cks.notes.domain.repository.FakeNoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GetNoteByIdUseCaseTest {
    private val repository = FakeNoteRepository()
    private val getNoteById = GetNoteByIdUseCase(repository)

    @Test
    fun `returns saved note by id`() = runBlocking {
        val id = repository.saveNote(
            Note(title = "Найди меня", content = "", createdAt = 1L, updatedAt = 1L)
        )

        val result = getNoteById(id)

        assertEquals("Найди меня", result?.title)
    }

    @Test
    fun `returns null for unknown id`() = runBlocking {
        assertNull(getNoteById(42L))
    }
}