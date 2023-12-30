package com.southernsunrise.notesapp.ui.fragments.notes.noteDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.southernsunrise.notesapp.data.repository.INoteRepository
import com.southernsunrise.notesapp.data.models.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteDetailsViewModel(private val noteRepository: INoteRepository) : ViewModel() {

    fun saveNoteChanges(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note.toNoteEntity())
        }
    }

}
