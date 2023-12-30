package com.southernsunrise.notesapp.data.repository

import com.southernsunrise.notesapp.data.entity.DrawingEntity
import kotlinx.coroutines.flow.Flow

interface IDrawingRepository {

    suspend fun insertDrawing(drawing: DrawingEntity)
    fun subscribeToAllDrawings(): Flow<List<DrawingEntity>>
    suspend fun deleteDrawing(id: Long?)
}