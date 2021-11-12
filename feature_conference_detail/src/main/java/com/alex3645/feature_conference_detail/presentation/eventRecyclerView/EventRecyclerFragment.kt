package com.alex3645.feature_conference_detail.presentation.eventRecyclerView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_detail.databinding.EventRecyclerListBinding
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailFragmentComponent
import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.recyclerView.EventRecyclerAdapter
import javax.inject.Inject

class EventRecyclerFragment(): Fragment() {

    private var conferenceId: Int? = null
    private var eventId: Int? = null
    private var parentNavController: NavController? = null

    constructor(id: Int, parentNavController: NavController) : this() {
        conferenceId = id
        this.parentNavController = parentNavController
    }

    @Inject
    lateinit var eventAdapter: EventRecyclerAdapter

    private val viewModel: EventRecyclerViewModel by viewModels()
    private var args: EventRecyclerFragmentArgs? = null

    private var _binding: EventRecyclerListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    private fun injectDependency() {
        DaggerConferenceDetailFragmentComponent.factory().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EventRecyclerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private  fun initRecycler(){
        eventAdapter.setOnDebouncedClickListener {
            parentNavController?.let {
                    it1 -> viewModel.navigateToEventByParent(it1,it.id?:0)
                    return@setOnDebouncedClickListener
            }
            viewModel.navigateToEventByNavigation(findNavController(),it.id?:0)
        }

        binding.eventRecyclerView.adapter = eventAdapter
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        conferenceId?.let{viewModel.conferenceId = it}

        observe(viewModel.stateLiveData, stateObserver)

        initRecycler()
        initView()
        viewModel.loadEventsForConference()
    }

    private fun initView(){
        if(parentNavController == null){
            val ar by navArgs<EventRecyclerFragmentArgs>()
            args = ar
        }

        args?.eventId?.let {
            eventId = it
            viewModel.eventId = it
        }
    }

    private val stateObserver = Observer<EventRecyclerViewModel.ViewState> {
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }else{
            eventAdapter.events = it.events!!
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}