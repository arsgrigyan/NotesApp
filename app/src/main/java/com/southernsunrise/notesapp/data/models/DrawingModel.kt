package com.southernsunrise.notesapp.data.models

import android.os.Parcelable
import com.southernsunrise.notesapp.data.entity.DrawingEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class DrawingModel(
    val id: Long,
    val createdDateInMillis: Long,
    val lastModifiedDateInMillis: Long,
    val drawingBitmapString: String
) : Parcelable {
    fun toDrawingEntity() = DrawingEntity(
        id = id,
        createdDateInMillis = createdDateInMillis,
        lastModifiedDateInMillis = lastModifiedDateInMillis,
        bitmapString = drawingBitmapString
    )
}
