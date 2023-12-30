package com.southernsunrise.notesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.southernsunrise.notesapp.data.dao.DrawingDao
import com.southernsunrise.notesapp.data.dao.NoteDao
import com.southernsunrise.notesapp.data.entity.DrawingEntity
import com.southernsunrise.notesapp.data.entity.NoteEntity

@Database(entities = [NoteEntity::class, DrawingEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun drawingDao(): DrawingDao
}