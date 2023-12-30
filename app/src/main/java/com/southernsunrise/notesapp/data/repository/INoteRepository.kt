package com.southernsunrise.notesapp.data.repository

import com.southernsunrise.notesapp.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface INoteRepository {

    suspend fun insertNotes(notes: List<NoteEntity>)
    suspend fun insertNote(note: NoteEntity)
    fun subscribeToAllNotes(): Flow<List<NoteEntity>>
    fun subscribeToAllStarredNotes(): Flow<List<NoteEntity>>
    suspend fun getNotes(ids: List<Long>): List<NoteEntity>
    suspend fun getNote(id: Long?): NoteEntity?
    suspend fun deleteAll()
    suspend fun deleteNote(id: Long?)
}