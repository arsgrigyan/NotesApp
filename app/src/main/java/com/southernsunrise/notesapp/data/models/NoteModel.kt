package com.southernsunrise.notesapp.data.models

import android.os.Parcelable
import com.southernsunrise.notesapp.data.entity.NoteEntity
import kotlinx.parcelize.Parcelize
import java.util.Calendar

@Parcelize
data class NoteModel(
    val id: Long,
    val title: String,
    val contentText: String,
    val createdDateInMillis: Long,
    // if note has not been edited, its last modification date is the same as that of the creation
    val updatedDateInMillis: Long = createdDateInMillis,
    val backgroundTintHex: String,
    var isStarred: Boolean = false
) : Parcelable {
    fun toNoteEntity() = NoteEntity(
        id = id,
        title = title,
        contentText = contentText,
        createdDateInMillis = createdDateInMillis,
        updatedDateInMillis = Calendar.getInstance().timeInMillis,
        isStarred = isStarred,
        cardBackgroundColorHex = backgroundTintHex
    )
}
