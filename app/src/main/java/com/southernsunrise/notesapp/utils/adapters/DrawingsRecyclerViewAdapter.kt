package com.southernsunrise.notesapp.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.southernsunrise.notesapp.databinding.DrawingCardRecyclerViewItemBinding
import com.southernsunrise.notesapp.data.models.DrawingModel
import com.southernsunrise.notesapp.utils.diffUtils.DrawingsDiffUtilsCallback
import com.southernsunrise.notesapp.utils.viewHolders.DrawingsViewHolder

class DrawingsRecyclerViewAdapter(
    private val onDrawingClickedCallback: (drawing: DrawingModel) -> Unit,
) :
    ListAdapter<DrawingModel, DrawingsViewHolder>(DrawingsDiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawingsViewHolder {
        return DrawingsViewHolder(
            onDrawingClickedCallback,
            DrawingCardRecyclerViewItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DrawingsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}