package com.n1cks.core.data.local.dataBase

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.n1cks.core.data.local.dao.NoteDao
import com.n1cks.core.data.local.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}

fun createNotesDatabase(context: Context): NotesDatabase =
    Room.databaseBuilder<NotesDatabase>(context, "notes.db")
        .setDriver(AndroidSQLiteDriver())
        .build()