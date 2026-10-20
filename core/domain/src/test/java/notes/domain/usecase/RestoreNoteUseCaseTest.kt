package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.model.Note
import com.n1cks.notes.domain.repository.FakeNoteRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RestoreNoteUseCaseTest {
    @Test
    fun `restored note keeps its id`() = runBlocking {
        val repository = FakeNoteRepository()
        val id = repository.saveNote(
            Note(
                title = "A",
                content = "",
                createdAt = 1L,
                updatedAt = 1L
            )
        )
        val note = requireNotNull(repository.getNoteById(id))
        repository.deleteNote(id)

        RestoreNoteUseCase(repository)(note)

        assertEquals(note, repository.getNoteById(id))
    }
}