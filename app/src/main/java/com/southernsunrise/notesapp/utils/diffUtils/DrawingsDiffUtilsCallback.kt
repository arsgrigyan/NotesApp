package com.southernsunrise.notesapp.utils.diffUtils

import androidx.recyclerview.widget.DiffUtil
import com.southernsunrise.notesapp.data.models.DrawingModel

class DrawingsDiffUtilsCallback : DiffUtil.ItemCallback<DrawingModel>() {

    override fun areItemsTheSame(oldItem: DrawingModel, newItem: DrawingModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DrawingModel, newItem: DrawingModel): Boolean {
        return oldItem == newItem
    }


}