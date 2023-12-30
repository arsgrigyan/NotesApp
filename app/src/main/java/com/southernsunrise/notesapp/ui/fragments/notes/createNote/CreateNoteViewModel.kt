package com.southernsunrise.notesapp.ui.fragments.notes.createNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.southernsunrise.notesapp.data.entity.NoteEntity
import com.southernsunrise.notesapp.data.repository.INoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateNoteViewModel(private val noteRepository: INoteRepository) : ViewModel() {

    fun addNewNote(title: String, noteContent: String, color: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentTimeInMillis = System.currentTimeMillis()
            val newNoteEntity = NoteEntity(
                title = title,
                contentText = noteContent,
                createdDateInMillis = currentTimeInMillis,
                updatedDateInMillis = currentTimeInMillis,
                isStarred = false,
                cardBackgroundColorHex = color
            )
            noteRepository.insertNote(newNoteEntity)
        }
    }

}
