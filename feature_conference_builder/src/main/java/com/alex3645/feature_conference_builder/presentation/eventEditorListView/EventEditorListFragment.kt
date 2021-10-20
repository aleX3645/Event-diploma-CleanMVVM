package com.alex3645.feature_conference_builder.presentation.eventEditorListView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.feature_conference_builder.databinding.FragmentEventEditorListBinding
import com.alex3645.feature_conference_builder.di.components.DaggerFragmentBuilderComponent
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Event
import com.alex3645.feature_conference_builder.presentation.eventEditorListView.recyclerView.EventAdapter
import javax.inject.Inject

class EventEditorListFragment : Fragment(){
    private val viewModel: EventEditorListViewModel by viewModels()

    private var _binding: FragmentEventEditorListBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<EventEditorListFragmentArgs>()

    @Inject
    lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    private fun injectDependency() {
        DaggerFragmentBuilderComponent.factory().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventEditorListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private  fun initRecycler(){
        binding.recyclerView.adapter = eventAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun initView(){

        args.conference?.let {
            viewModel.conference = it
        }

        args.event?.let{
            viewModel.event = it
        }

        initBackStackObserver()
        initRecycler()
        initActions()
    }

    private fun initBackStackObserver(){
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Event>("event")
            ?.observe(viewLifecycleOwner) {
                viewModel.event = it
                eventAdapter.events = viewModel.event!!.events ?: mutableListOf()
            }

        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Conference>("conference")
            ?.observe(viewLifecycleOwner) {
                Log.d("eventList", it.events?.size.toString())
                viewModel.conference = it
                eventAdapter.events = viewModel.conference!!.events ?: mutableListOf()
            }


    }

    private fun initActions(){
        binding.floatingActionButton.setOnClickListener {
            viewModel.navigateToEventEditor(findNavController())
        }

        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this.isEnabled = false
                viewModel.navigateBack(findNavController())
            }
        })
    }
}