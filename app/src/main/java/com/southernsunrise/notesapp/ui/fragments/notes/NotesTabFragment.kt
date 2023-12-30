package com.southernsunrise.notesapp.ui.fragments.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.southernsunrise.notesapp.databinding.FragmentNotesTabBinding

class NotesTabFragment : Fragment() {

    private var _binding: FragmentNotesTabBinding? = null
    private val binding get() = _binding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNotesTabBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}