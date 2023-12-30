package com.southernsunrise.notesappbottomnav.fragments.notes.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.southernsunrise.notesapp.data.repository.INoteRepository
import com.southernsunrise.notesapp.data.models.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NotesViewModel(private val noteRepository: INoteRepository) : ViewModel() {

    var notesFlow: Flow<List<NoteModel>> = noteRepository.subscribeToAllNotes()
        .map { noteEntities -> noteEntities.map { noteEntity -> noteEntity.toNoteModel() } }

    fun updateNoteStarredState(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote((note.apply { isStarred = !isStarred }).toNoteEntity())
        }
    }

    fun removeNote(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(id)
        }
    }

}
