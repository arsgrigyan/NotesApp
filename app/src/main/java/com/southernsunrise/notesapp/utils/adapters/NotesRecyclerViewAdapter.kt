package com.southernsunrise.notesapp.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.southernsunrise.notesapp.databinding.NotesCardRecyclerViewItemBinding
import com.southernsunrise.notesapp.data.models.NoteModel
import com.southernsunrise.notesapp.utils.diffUtils.NotesDiffUtilsCallback
import com.southernsunrise.notesapp.utils.viewHolders.NotesViewHolder

class NotesRecyclerViewAdapter(
    val onNoteTappedCallback: (note: NoteModel) -> Unit,
    private val onNoteStarredStateChangeCallback: (noteModel: NoteModel) -> Unit
) :
    ListAdapter<NoteModel, NotesViewHolder>(NotesDiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            onNoteTappedCallback,
            onNoteStarredStateChangeCallback,
            NotesCardRecyclerViewItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}