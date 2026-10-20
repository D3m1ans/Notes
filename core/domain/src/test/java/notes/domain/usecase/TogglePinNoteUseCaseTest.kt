package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.model.Note
import com.n1cks.notes.domain.repository.FakeNoteRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TogglePinNoteUseCaseTest {
    private val repository = FakeNoteRepository()
    private val togglePin = TogglePinNoteUseCase(repository)

    @Test
    fun `toggles pin and keeps updatedAt`() = runBlocking {
        val id = repository.saveNote(
            Note(
                title = "A",
                content = "",
                createdAt = 1L,
                updatedAt = 1L
            )
        )

        togglePin(id)
        assertEquals(true, repository.getNoteById(id)?.isPinned)

        togglePin(id)
        assertEquals(false, repository.getNoteById(id)?.isPinned)
        assertEquals(1L, repository.getNoteById(id)?.updatedAt)
    }
}