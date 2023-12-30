@file:Suppress("RemoveExplicitTypeArguments")

package com.southernsunrise.notesapp.di

import androidx.room.Room
import com.southernsunrise.notesapp.application.NotesApplication
import com.southernsunrise.notesapp.data.dao.DrawingDao
import com.southernsunrise.notesapp.data.dao.NoteDao
import com.southernsunrise.notesapp.data.database.AppDatabase
import com.southernsunrise.notesapp.data.repository.DrawingRepositoryImpl
import com.southernsunrise.notesapp.data.repository.IDrawingRepository
import com.southernsunrise.notesapp.data.repository.INoteRepository
import com.southernsunrise.notesapp.data.repository.NoteRepositoryImpl
import org.koin.dsl.module

val dataModule = module {

    /** AppDatabase */
    single<AppDatabase> {
        Room.databaseBuilder(
            NotesApplication.applicationContext(),
            AppDatabase::class.java,
            "notes_database"
        ).build()
    }

    /** Dao */
    single<NoteDao> { get<AppDatabase>().noteDao() }
    single<DrawingDao> { get<AppDatabase>().drawingDao() }


    /** Repository */
    single<INoteRepository> {
        NoteRepositoryImpl(get<NoteDao>())
    }
    single<IDrawingRepository> {
        DrawingRepositoryImpl(get<DrawingDao>())
    }
}
