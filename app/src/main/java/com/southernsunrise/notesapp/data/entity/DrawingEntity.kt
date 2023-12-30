package com.southernsunrise.notesapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.southernsunrise.notesapp.data.models.DrawingModel

@Entity(tableName = "drawing_entity")
data class DrawingEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "created_date") val createdDateInMillis: Long,
    @ColumnInfo(name = "updated_date") val lastModifiedDateInMillis: Long,
    @ColumnInfo(name = "bitmap_string") val bitmapString: String,

    ) {
    fun toDrawingModel() = DrawingModel(
        id = id,
        createdDateInMillis = createdDateInMillis,
        lastModifiedDateInMillis = lastModifiedDateInMillis,
        drawingBitmapString = bitmapString
    )
}
