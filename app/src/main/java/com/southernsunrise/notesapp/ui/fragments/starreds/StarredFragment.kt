package com.southernsunrise.notesapp.ui.fragments.starreds

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.southernsunrise.notesapp.databinding.FragmentStarredBinding
import com.southernsunrise.notesapp.data.models.NoteModel
import com.southernsunrise.notesapp.utils.adapters.NotesRecyclerViewAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StarredFragment : Fragment() {

    private var _binding: FragmentStarredBinding? = null
    private val binding get() = _binding!!

    private val starredNotesViewModel by viewModel<StarredViewModel>()
    private var starredNotesRecyclerViewAdapter: NotesRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStarredBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        setupNotesRecyclerView()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            starredNotesViewModel.starredNotesFlow.collectLatest { notes ->
                binding.viewNothingInStarred.isVisible = notes.isEmpty()
                updateRecyclerView(notes)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecyclerView(notesList: List<NoteModel>) {
        starredNotesRecyclerViewAdapter?.apply {
            submitList(notesList)
            notifyDataSetChanged()
        }
    }

    private fun setupNotesRecyclerView() {
        starredNotesRecyclerViewAdapter =
            NotesRecyclerViewAdapter(
                onNoteTappedCallback = ::onNoteTapCallback,
                onNoteStarredStateChangeCallback = ::removeNoteFromStarred
            )
        binding.notesRecyclerView.adapter = starredNotesRecyclerViewAdapter
    }

    private fun onNoteTapCallback(note: NoteModel) {
        findNavController().navigate(
            StarredFragmentDirections.actionStarredFragmentToNoteDetailsFragment(
                note
            )
        )
    }

    private fun removeNoteFromStarred(note: NoteModel) {
        starredNotesViewModel.removeNoteFromStarred(note = note)
//        starredNotesRecyclerViewAdapter?.notifyItemRemoved(starredNotesList.indexOf(note))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        starredNotesRecyclerViewAdapter = null
    }

}