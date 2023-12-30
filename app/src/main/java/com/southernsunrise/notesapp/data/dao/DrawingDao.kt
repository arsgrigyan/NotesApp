package com.southernsunrise.notesapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.southernsunrise.notesapp.data.entity.DrawingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DrawingDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrawing(drawing: DrawingEntity)

    @Query("SELECT * FROM drawing_entity")
    fun subscribeToAllDrawings(): Flow<List<DrawingEntity>>

    @Query("DELETE FROM drawing_entity WHERE id = :id")
    suspend fun deleteDrawing(id: Long?)
}
