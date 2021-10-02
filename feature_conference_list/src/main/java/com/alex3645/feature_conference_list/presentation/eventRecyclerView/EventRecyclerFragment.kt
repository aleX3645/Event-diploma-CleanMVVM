package com.alex3645.feature_conference_list.presentation.eventRecyclerView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.feature_conference_list.di.component.DaggerConferenceListFragmentComponent
import com.alex3645.feature_conference_list.presentation.eventRecyclerView.recyclerView.EventRecyclerAdapter
import com.alex3645.feature_event_list.databinding.EventRecyclerListBinding
import javax.inject.Inject

class EventRecyclerFragment : Fragment() {

    @Inject
    lateinit var eventAdapter: EventRecyclerAdapter

    private val viewModel: EventRecyclerViewModel by viewModels()
    private val args by navArgs<EventRecyclerFragmentArgs>()

    private var _binding: EventRecyclerListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    private fun injectDependency() {
        DaggerConferenceListFragmentComponent.factory().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EventRecyclerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
    }

    private  fun initRecycler(){

        eventAdapter.events = args.conference.events!!

        eventAdapter.setOnDebouncedClickListener {
            viewModel.navigateToEvent(findNavController(),it)
        }

        binding.eventRecyclerView.adapter = eventAdapter
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}