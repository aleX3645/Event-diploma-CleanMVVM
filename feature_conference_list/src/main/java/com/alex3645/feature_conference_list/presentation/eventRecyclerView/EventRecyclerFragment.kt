package com.alex3645.feature_conference_list.presentation.eventRecyclerView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alex3645.feature_conference_list.presentation.eventRecyclerView.EventRecyclerViewModel
import com.alex3645.feature_event_list.databinding.FragmentRecyclerListBinding

class EventRecyclerFragment: Fragment() {

    private var _binding: FragmentRecyclerListBinding? = null
    private val binding get() = _binding!!

    val viewModel: EventRecyclerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}