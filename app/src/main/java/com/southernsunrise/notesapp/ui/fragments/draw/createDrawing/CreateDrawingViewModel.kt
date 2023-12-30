package com.southernsunrise.notesapp.ui.fragments.draw.createDrawing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.southernsunrise.notesapp.data.entity.DrawingEntity
import com.southernsunrise.notesapp.data.repository.IDrawingRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CreateDrawingViewModel(private val drawingRepository: IDrawingRepository) : ViewModel() {

    fun addDrawing(drawingBitmapString: String, drawingInsertionCompletedCallback: () -> Unit) {
        val drawingInsertionJob: Job = viewModelScope.launch {
            drawingRepository.insertDrawing(
                DrawingEntity(
                    createdDateInMillis = System.currentTimeMillis(),
                    lastModifiedDateInMillis = System.currentTimeMillis(),
                    bitmapString = drawingBitmapString
                )
            )
        }
        drawingInsertionJob.invokeOnCompletion {
            drawingInsertionCompletedCallback.invoke()
        }
    }

}