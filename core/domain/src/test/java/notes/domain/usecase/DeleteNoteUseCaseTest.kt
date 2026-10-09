package com.n1cks.notes.domain.usecase

import com.n1cks.notes.domain.model.Note
import com.n1cks.notes.domain.repository.FakeNoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteNoteUseCaseTest {
    private val repository = FakeNoteRepository()

    @Test
    fun `deleted note disappears from the list`() = runBlocking {
        val id = repository.saveNote(
            Note(title = "Удалить", content = "", createdAt = 1L, updatedAt = 1L)
        )
        val deletedNote = DeleteNoteUseCase(repository)
    }
}