package com.southernsunrise.notesapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.southernsunrise.notesapp.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<NoteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Query("SELECT * FROM note_entity")
    fun subscribeToAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_entity WHERE is_starred = 1 ORDER BY created_date ASC")
    fun subscribeToAllStarredNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_entity WHERE  id IN (:ids) ORDER BY created_date ASC")
    suspend fun getNotes(ids: List<Long>): List<NoteEntity>

    @Query("SELECT * FROM note_entity WHERE id = :id")
    suspend fun getNote(id: Long?): NoteEntity?

    @Query("DELETE FROM note_entity")
    suspend fun deleteAll()

    @Query("DELETE FROM note_entity WHERE id = :id")
    suspend fun deleteNote(id: Long?)
}
