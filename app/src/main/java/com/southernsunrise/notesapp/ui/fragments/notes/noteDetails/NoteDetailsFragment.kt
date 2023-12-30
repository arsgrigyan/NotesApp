package com.southernsunrise.notesapp.ui.fragments.notes.noteDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.southernsunrise.notesapp.R
import com.southernsunrise.notesapp.ui.activities.NotesMainActivity
import com.southernsunrise.notesapp.databinding.FragmentNoteDetailsBinding
import com.southernsunrise.notesapp.data.models.NoteModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoteDetailsFragment() : Fragment() {

    private var _binding: FragmentNoteDetailsBinding? = null
    private val binding get() = _binding!!
    private val detailsArgs: NoteDetailsFragmentArgs by navArgs()
    private val noteDetailsViewModel by viewModel<NoteDetailsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setOnBackPressedCallback()
    }

    private fun setOnBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
    }

    private fun setupViews() {
        setupEditTexts()
        setupBackButton()
        setupEditTextView()
    }

    private fun setupEditTextView() = with(binding) {

        editSaveTextview.setOnClickListener {

            noteTitleEditText.isEnabled = !noteTitleEditText.isEnabled
            noteContentEditText.isEnabled = !noteContentEditText.isEnabled

            if (noteContentEditText.isEnabled) {
                noteContentEditText.apply {
                    setSelection(length())
                    requestFocus()
                }
            }

            if (editTextsInputChanged()) {
                // save changes and pop the details fragment
                detailsArgs.note.apply {
                    noteDetailsViewModel.saveNoteChanges(
                        NoteModel(
                            id = id,
                            title = binding.noteTitleEditText.text.toString().trim(),
                            contentText = binding.noteContentEditText.text.toString().trim(),
                            createdDateInMillis = createdDateInMillis,
                            // take a random color from the given note card background colors for the newly added note background
                            backgroundTintHex = backgroundTintHex,
                            isStarred = isStarred
                        )
                    )
                }
                findNavController().popBackStack()
            }
        }
    }

    private fun setupEditTexts() {
        binding.apply {
            noteTitleEditText.apply {
                setText(detailsArgs.note.title)
                setOnTextChangeListener()
            }
            noteContentEditText.apply {
                setText(detailsArgs.note.contentText)
                setOnTextChangeListener()
            }
        }
    }

    private fun EditText.setOnTextChangeListener() {
        this.addTextChangedListener {
            it?.let {
                binding.editSaveTextview.text = if (editTextsInputChanged()) {
                    context.getText(R.string.save)
                } else {
                    context.getText(R.string.edit)
                }
            }
        }
    }

    private fun editTextsInputChanged() =
        binding.noteTitleEditText.text.toString() != detailsArgs.note.title || binding.noteContentEditText.text.toString() != detailsArgs.note.contentText


    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            if (editTextsInputChanged()) showNoteCreateDiscardDialog()
            else findNavController().popBackStack()
        }
    }

    private fun showNoteCreateDiscardDialog() {
        val alertDialog =
            MaterialAlertDialogBuilder(requireContext()).setTitle("Are you sure you want to discard?")
                .setMessage("data you have input will be lost.")
                .setPositiveButton("Yes") { dialog, _ -> findNavController().popBackStack() }
                .setNegativeButton("Cancel", /* listener = */ null)
        alertDialog.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // setting the dispatcher to the NotesMainActivity default
        (requireActivity() as com.southernsunrise.notesapp.ui.activities.NotesMainActivity).setOnBackPressedDispatcher()
    }
}