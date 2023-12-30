package com.southernsunrise.notesapp.utils.viewHolders

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.southernsunrise.notesapp.databinding.DrawingCardRecyclerViewItemBinding
import com.southernsunrise.notesapp.data.models.DrawingModel
import com.southernsunrise.notesapp.utils.BitmapUtils.convertStringToBitmap
import kotlin.io.encoding.ExperimentalEncodingApi

class DrawingsViewHolder(
    val onDrawingClickedCallback: (drawing: DrawingModel) -> Unit,
    private val itemViewBinding: DrawingCardRecyclerViewItemBinding
) :
    RecyclerView.ViewHolder(itemViewBinding.root) {
    @OptIn(ExperimentalEncodingApi::class)
    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
    fun bind(drawingModel: DrawingModel) = with(itemViewBinding) {
        drawingImageView.setImageBitmap(drawingModel.drawingBitmapString.convertStringToBitmap())
        root.setOnClickListener {
            onDrawingClickedCallback.invoke(drawingModel)
        }
    }
}
