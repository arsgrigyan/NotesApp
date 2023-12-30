package com.southernsunrise.notesapp.utils.viewHolders

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.southernsunrise.notesapp.R
import com.southernsunrise.notesapp.databinding.NotesCardRecyclerViewItemBinding
import com.southernsunrise.notesapp.data.models.NoteModel

class NotesViewHolder(
    val onNoteTappedCallback: (note: NoteModel) -> Unit,
    private val onNoteStarredStateChangeCallback: (noteModel: NoteModel) -> Unit,
    private val itemViewBinding: NotesCardRecyclerViewItemBinding
) :
    RecyclerView.ViewHolder(itemViewBinding.root) {
    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
    fun bind(noteModel: NoteModel) = with(itemViewBinding) {

        noteModel.apply {
            val colorInt = Color.parseColor(backgroundTintHex)
            cardRoot.apply {
                setCardBackgroundColor(colorInt)
            }
            noteTitleTextView.text = title
            noteContentTextView.text = contentText

            if (isStarred) {
                addToStarredButton.setImageResource(R.drawable.ic_starred)
            } else addToStarredButton.setImageResource(R.drawable.ic_unstarred)

            addToStarredButton.setOnClickListener {
                if (isStarred) {
                    addToStarredButton.setImageResource(R.drawable.ic_unstarred)
                } else addToStarredButton.setImageResource(R.drawable.ic_starred)
                onNoteStarredStateChangeCallback(noteModel)
            }
            cardRoot.setOnClickListener { onNoteTappedCallback(noteModel) }

        }


    }
}
