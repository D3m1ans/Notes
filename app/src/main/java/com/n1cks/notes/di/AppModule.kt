package com.n1cks.notes.di

import android.content.Context
import com.n1cks.core.data.factory.createNoteRepository
import com.n1cks.notes.domain.repository.NoteRepository
import com.n1cks.notes.domain.usecase.AddNoteUseCase
import com.n1cks.notes.domain.usecase.DeleteNoteUseCase
import com.n1cks.notes.domain.usecase.GetNoteByIdUseCase
import com.n1cks.notes.domain.usecase.ObserveNotesUseCase
import com.n1cks.notes.domain.usecase.RestoreNoteUseCase
import com.n1cks.notes.domain.usecase.TogglePinNoteUseCase
import com.n1cks.notes.domain.usecase.UpdateNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteRepository(@ApplicationContext context: Context): NoteRepository =
        createNoteRepository(context)

    @Provides
    fun provideObserveNotesUseCase(repository: NoteRepository): ObserveNotesUseCase =
        ObserveNotesUseCase(repository)

    @Provides
    fun provideAddNoteUseCase(repository: NoteRepository): AddNoteUseCase =
        AddNoteUseCase(repository)

    @Provides
    fun provideDeleteNoteUseCase(repository: NoteRepository): DeleteNoteUseCase =
        DeleteNoteUseCase(repository)

    @Provides
    fun provideGetNoteByIdUseCase(repository: NoteRepository): GetNoteByIdUseCase =
        GetNoteByIdUseCase(repository)


    @Provides
    fun provideUpdateNoteUseCase(repository: NoteRepository): UpdateNoteUseCase =
        UpdateNoteUseCase(repository)

    @Provides
    fun provideTogglePinNoteUseCase(repository: NoteRepository): TogglePinNoteUseCase =
        TogglePinNoteUseCase(repository)

    @Provides
    fun provideRestoreNoteUseCase(repository: NoteRepository): RestoreNoteUseCase =
        RestoreNoteUseCase(repository)
}