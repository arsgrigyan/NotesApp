package com.southernsunrise.notesappbottomnav.fragments.draw.drawDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.southernsunrise.notesapp.data.entity.DrawingEntity
import com.southernsunrise.notesapp.data.repository.IDrawingRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DrawingDetailsViewModel(private val drawingRepository: IDrawingRepository) : ViewModel() {

    fun updateDrawing(id:Long, drawingBitmapString: String, drawingUpdateCompletedCallback: () -> Unit) {
        val drawingInsertionJob: Job = viewModelScope.launch {
            drawingRepository.insertDrawing(
                DrawingEntity(
                    id = id,
                    createdDateInMillis = System.currentTimeMillis(),
                    lastModifiedDateInMillis = System.currentTimeMillis(),
                    bitmapString = drawingBitmapString
                )
            )
        }
        drawingInsertionJob.invokeOnCompletion {
            drawingUpdateCompletedCallback.invoke()
        }
    }

}