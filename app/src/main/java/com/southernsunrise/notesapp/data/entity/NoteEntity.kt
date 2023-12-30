package com.southernsunrise.notesapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.southernsunrise.notesapp.data.models.NoteModel

@Entity(tableName = "note_entity")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "note") val contentText: String,
    @ColumnInfo(name = "created_date") val createdDateInMillis: Long,
    @ColumnInfo(name = "updated_date") val updatedDateInMillis: Long,
    @ColumnInfo(name = "is_starred") val isStarred: Boolean,
    @ColumnInfo(name = "card_background_color_hex") val cardBackgroundColorHex: String
) {
    fun toNoteModel() = NoteModel(
        id = id,
        title = title,
        contentText = contentText,
        createdDateInMillis = createdDateInMillis,
        updatedDateInMillis = updatedDateInMillis,
        backgroundTintHex = cardBackgroundColorHex,
        isStarred = isStarred
    )
}
