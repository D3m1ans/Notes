package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.model.InvalidNoteException
import com.n1cks.notes.domain.model.Note
import com.n1cks.notes.domain.repository.FakeNoteRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Test

class UpdateNoteUseCaseTest {
    private val repository = FakeNoteRepository()

    @Test
    fun `updates text and updatedAt, keeps createAt and pin`() = runBlocking {
        val id = repository.saveNote(
            Note(
                title = "Old",
                content = "Old Text",
                isPinned = true,
                createdAt = 1_000L,
                updatedAt = 1_000L
            )
        )

        val updateNote = UpdateNoteUseCase(repository, currentTimeMillis = { 5_000L })
        updateNote(id, "  New  ", "New text")

        val saved = repository.getNoteById(id)
        assertEquals("New", saved?.title)
        assertEquals("New text", saved?.content)
        assertEquals(5_000L, saved?.updatedAt)
        assertEquals(1_000L, saved?.createdAt)
        assertEquals(true, saved?.isPinned)
    }

    @Test
    fun `blank title and content throws InvalidNoteException`() {
        val updateNote = UpdateNoteUseCase(repository)

        assertThrows(InvalidNoteException::class.java) {
            runBlocking { updateNote(1L, "  ", "") }
        }
    }

    @Test
    fun `unknown id throws InvalidNoteException`() {
        val updateNote = UpdateNoteUseCase(repository)

        assertThrows(InvalidNoteException::class.java) {
            runBlocking { updateNote(42L, "Tittle", "Text") }
        }
    }
}