package com.n1cks.core.data.repository

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.n1cks.core.data.local.dataBase.NotesDatabase
import com.n1cks.notes.domain.model.Note
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomNoteRepositoryTest {

    private lateinit var db: NotesDatabase
    private lateinit var repository: RoomNoteRepository

    @Before
    fun crateDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder<NotesDatabase>(context)
            .setDriver(AndroidSQLiteDriver())
            .build()
        repository = RoomNoteRepository(db.noteDao())
    }

    @After
    fun closeDb() {
        db.close()
    }

    private fun note(title: String, id: Long = 0L) =
        Note(id = id, title = title, content = "", createdAt = 1L, updatedAt = 1L)

    @Test
    fun newNoteGetsGeneratedId() = runBlocking {
        val id = repository.saveNote(note("a"))

        assertEquals(1L, id)
        assertEquals("a", repository.getNoteById(id)?.title)
    }

    @Test
    fun savingExistingNoteKeepsIdAndUpdatesIt() = runBlocking {
        val id = repository.saveNote(note("old"))

        val returnedId = repository.saveNote(note("new", id = id))

        assertEquals(id, returnedId)
        assertEquals(listOf("new"), repository.observeNotes().first().map { it.title })
    }

    @Test
    fun deletedNoteIsGone() = runBlocking {
        val id = repository.saveNote(note("a"))

        repository.deleteNote(id)

        assertNull(repository.getNoteById(id))
    }

    @Test
    fun restoredNoteKeepsItsId() = runBlocking {
        val id = repository.saveNote(note("a"))
        val saved = requireNotNull(repository.getNoteById(id))
        repository.deleteNote(id)

        repository.saveNote(saved)

        assertEquals(saved, repository.getNoteById(id))
    }
}