package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.model.InvalidNoteException
import com.n1cks.notes.domain.repository.FakeNoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class AddNoteUseCaseTest {
    private val repository = FakeNoteRepository()

    @Test
    fun `blank title and content throws InvalidNoteException`() {
        val addNote = AddNoteUseCase(repository)

        assertThrows(InvalidNoteException::class.java) {
            runBlocking { addNote("   ", "") }
        }
    }

    @Test
    fun `title and content are trimmed`() = runBlocking {
        val addNote = AddNoteUseCase(repository)

        val id = addNote("  Привет  ", "  мир  ")

        val saved = repository.getNoteById(id)
        assertEquals("Привет", saved?.title)
        assertEquals("мир", saved?.content)
    }

    @Test
    fun `timestamps come from the provided clock`() = runBlocking {
        val addNote = AddNoteUseCase(repository, currentTimeMillis = { 1_000L })

        val id = addNote("Заголовок", "Текст")

        val saved = repository.getNoteById(id)
        assertEquals(1_000L, saved?.createdAt)
        assertEquals(1_000L, saved?.updatedAt)
    }
}