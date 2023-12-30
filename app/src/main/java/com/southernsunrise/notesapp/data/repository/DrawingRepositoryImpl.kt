package com.southernsunrise.notesapp.data.repository

import com.southernsunrise.notesapp.data.dao.DrawingDao
import com.southernsunrise.notesapp.data.entity.DrawingEntity
import kotlinx.coroutines.flow.Flow

class DrawingRepositoryImpl(private val drawingDao: DrawingDao) : IDrawingRepository {

    override suspend fun insertDrawing(drawing: DrawingEntity) {
        drawingDao.insertDrawing(drawing)
    }

    override fun subscribeToAllDrawings(): Flow<List<DrawingEntity>> {
        return drawingDao.subscribeToAllDrawings()
    }


    override suspend fun deleteDrawing(id: Long?) {
        drawingDao.deleteDrawing(id)
    }

}
