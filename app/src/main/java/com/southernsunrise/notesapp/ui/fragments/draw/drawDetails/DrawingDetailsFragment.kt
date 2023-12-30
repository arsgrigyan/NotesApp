package com.southernsunrise.notesapp.ui.fragments.draw.drawDetails

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.addCallback
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.southernsunrise.notesapp.R
import com.southernsunrise.notesapp.ui.activities.NotesMainActivity
import com.southernsunrise.notesapp.databinding.FragmentDrawingDetailsBinding
import com.southernsunrise.notesapp.utils.BitmapUtils.convertStringToBitmap
import com.southernsunrise.notesappbottomnav.fragments.draw.drawDetails.DrawingDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DrawingDetailsFragment : Fragment() {
    private var _binding: FragmentDrawingDetailsBinding? = null
    private val binding get() = _binding!!
    private val drawingDetailsArgs: DrawingDetailsFragmentArgs by navArgs()
    private val drawingDetailsViewModel by viewModel<DrawingDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrawingDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setOnBackPressedCallback()
    }

    private fun setupViews() {
        setupDrawSizeSeekbar()
        setupButtons()
        setupDrawingView()
    }

    private fun setupDrawingView() {
        binding.drawingView.setBitmap(drawingDetailsArgs.drawing.drawingBitmapString.convertStringToBitmap())
    }

    private fun setupButtons() = with(binding) {
        colorPickButton.setOnClickListener {
            showColorPicker()
        }
        clearAllImageButton.setOnClickListener {
            clearDrawing()
        }
        saveTextview.setOnClickListener {
            updatingDrawingProgressbar.isVisible = true
            drawingView.getDrawingBitmapString()
                ?.let { drawingBitmapStr ->
                    drawingDetailsViewModel.updateDrawing(
                        id = drawingDetailsArgs.drawing.id,
                        drawingBitmapString = drawingBitmapStr,
                        drawingUpdateCompletedCallback = {
                            updatingDrawingProgressbar.isGone = true
                        }
                    )
                    findNavController().popBackStack()

                }

        }

        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupDrawSizeSeekbar() {
        binding.drawSizeChangeSeekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            //            var progress = 0
            @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar?.apply {
                    this.progress = progress
                    val ovalDrawable = getDrawable(requireContext(), R.drawable.shape_oval)
                    val thumbBitmap = ovalDrawable?.toBitmap(progress, progress)
                    val thumbDrawable = BitmapDrawable(resources, thumbBitmap)
                    changeDrawWidth(progress.toFloat())
                    thumb = thumbDrawable
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                seekBar?.setPaddingRelative(0, 0, progress, 0)
            }

        })

    }

    private fun showColorPicker() {
        ColorPickerDialog
            .Builder(requireContext())
            .setTitle("Pick Theme")
            .setColorShape(ColorShape.CIRCLE)
            .setDefaultColor(R.color.color_secondary)
            .setColorListener { color, colorHex ->
                changeDrawColor(color)
                binding.colorPickButton.setCardBackgroundColor(color)
            }
            .show()
    }

    fun changeDrawWidth(width: Float) = binding.drawingView.setDrawWidth(width)
    private fun changeDrawColor(@ColorInt color: Int) = binding.drawingView.setPaintColor(color)

    private fun clearDrawing() = binding.drawingView.clearDrawing()

    private fun setOnBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // setting the dispatcher to the NotesMainActivity default
        (requireActivity() as com.southernsunrise.notesapp.ui.activities.NotesMainActivity).setOnBackPressedDispatcher()
    }


}