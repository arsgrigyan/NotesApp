package com.southernsunrise.notesapp.ui.fragments.draw.draw

import androidx.lifecycle.ViewModel
import com.southernsunrise.notesapp.data.repository.IDrawingRepository
import com.southernsunrise.notesapp.data.models.DrawingModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DrawViewModel(private val drawingRepository: IDrawingRepository) : ViewModel() {

    var drawingsFlow: Flow<List<DrawingModel>> = drawingRepository.subscribeToAllDrawings()
        .map { drawingEntities -> drawingEntities.map { drawingEntity -> drawingEntity.toDrawingModel() } }

}