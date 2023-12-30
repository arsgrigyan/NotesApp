package com.southernsunrise.notesapp.ui.fragments.notes.createNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.smparkworld.parkdatetimepicker.ParkDateTimePicker
import com.southernsunrise.notesapp.R
import com.southernsunrise.notesapp.ui.activities.NotesMainActivity
import com.southernsunrise.notesapp.application.NotesApplication
import com.southernsunrise.notesapp.databinding.FragmentCreateNoteBinding
import com.southernsunrise.notesapp.workmanager.NoteReminderWorker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import java.util.concurrent.TimeUnit


class CreateNoteFragment : Fragment() {

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!
    private val createNoteViewModel by viewModel<CreateNoteViewModel>()

    private var noteReminderNotificationShowDateInMillis: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setOnBackPressedCallback()
    }

    private fun setupViews() {
        setupEditTexts()
        setClickListeners()
    }

    private fun setupEditTexts() {
        binding.apply {
            noteTitleEditText.setOnTextChangeListener()
            noteContentEditText.setOnTextChangeListener()
        }
    }

    private fun EditText.setOnTextChangeListener() {
        this.addTextChangedListener {
            it?.let {
                binding.doneButton.isEnabled = editTextsInputValid()
            }
        }
    }

    private fun editTextsInputValid() =
        binding.noteTitleEditText.text.toString()
            .isNotBlank() && binding.noteContentEditText.text.toString().isNotBlank()


    private fun setOnBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setClickListeners() = with(binding) {
        doneButton.setOnClickListener {
            val colorList = resources.getStringArray(R.array.notes_cards_background_color_hex_codes)
            createNoteViewModel.addNewNote(
                noteTitleEditText.text.toString().trim(),
                noteContentEditText.text.toString().trim(),
                colorList.random()
            )
            if (noteReminderNotificationShowDateInMillis != 0L) setReminderNotificationShowWorkRequest()
          //  showToast(noteReminderNotificationShowDateInMillis.toString())
            requireActivity().supportFragmentManager.popBackStack()
        }
        backButton.setOnClickListener {
            if (noteTitleEditText.text.isNotEmpty() || noteContentEditText.text.isNotEmpty()) showNoteCreateDiscardDialog()
            else requireActivity().supportFragmentManager.popBackStack()
        }

        addReminderButton.setOnClickListener {
            showDateTimePickerDialog()
        }
    }

    private fun setReminderNotificationShowWorkRequest() {
        val workTag = "noteRemindWork"
        val inputData: Data =
            Data.Builder().putString("NOTE_TITLE", binding.noteTitleEditText.text.toString())
                .build()

        val notificationWork: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<NoteReminderWorker>()
                .setInputData(inputData)
                .addTag(workTag)
                .setInitialDelay(
                    noteReminderNotificationShowDateInMillis - System.currentTimeMillis(),
                    TimeUnit.MILLISECONDS
                )
                .build()

        WorkManager.getInstance(NotesApplication.applicationContext()).enqueue(notificationWork);

    }
    private fun showDateTimePickerDialog() {
        ParkDateTimePicker.builder(this)
            .setTitle("Set Reminder")
            .setDayOfWeekTexts(arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"))
            .setAmPmTexts(arrayOf("AM", "PM"))
            .setResetText("Reset")
            .setDoneText("Done")
            .setMonthTitleFormatter { year, month ->
                "${year}-${String.format("%02d", month)}"
            }
            .setDateResultFormatter { year, month, day ->
                "${year}-${String.format("%02d", month)}-${String.format("%02d", day)}"
            }
            .setTimeResultFormatter { amPm, hour, minute ->
                "${amPm} ${String.format("%02d", hour)}h ${String.format("%02d", minute)}m"
            }
            .setDateTimeListener { dateTime ->
                val calendar = Calendar.getInstance();
                calendar.set(
                    dateTime.year, dateTime.month, dateTime.month,
                    dateTime.hour, dateTime.minute, 0
                );
                noteReminderNotificationShowDateInMillis = calendar.timeInMillis
            }
            .show()
    }

    private fun showNoteCreateDiscardDialog() {
        val alertDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Are you sure you want to discard?")
            .setMessage("data you have input will be lost.")
            .setPositiveButton("Yes") { dialog, _ -> requireActivity().supportFragmentManager.popBackStack() }
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