package com.southernsunrise.notesapp.ui.fragments.draw.draw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.southernsunrise.notesapp.databinding.FragmentDrawBinding
import com.southernsunrise.notesapp.data.models.DrawingModel
import com.southernsunrise.notesapp.utils.adapters.DrawingsRecyclerViewAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DrawFragment : Fragment() {

    private var _binding: FragmentDrawBinding? = null
    private val binding get() = _binding!!
    private var drawAdapter: DrawingsRecyclerViewAdapter? = null

    private val drawViewModel by viewModel<DrawViewModel>()
    private val drawingsList = ArrayList<DrawingModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrawBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    private fun setupObservers() = with(drawViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            drawingsFlow.collectLatest { drawings ->
                drawings.forEach { note -> drawingsList.add(note) }
                binding.viewNothingInDrawings.isVisible = drawings.isEmpty()
                updateDrawingsRecyclerView(drawings)
            }
        }
    }

    private fun updateDrawingsRecyclerView(drawings: List<DrawingModel>) {
        drawAdapter?.apply {
            submitList(drawings)
            notifyDataSetChanged()
        }
    }

    private fun setupViews() {
        setupDrawingsRecyclerView()
    }

    private fun setupDrawingsRecyclerView() = with(binding) {
        drawAdapter = DrawingsRecyclerViewAdapter(::onDrawingItemClicked)
        drawingsRecyclerView.apply {
            adapter = drawAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun onDrawingItemClicked(drawing: DrawingModel) {

        findNavController().navigate(
            DrawFragmentDirections.actionDrawFragmentToDrawingDetailsFragment(
                drawing
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        drawAdapter = null
    }
}