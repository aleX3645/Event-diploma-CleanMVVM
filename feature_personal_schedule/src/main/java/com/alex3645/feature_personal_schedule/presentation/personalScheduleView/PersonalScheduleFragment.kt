package com.alex3645.feature_personal_schedule.presentation.personalScheduleView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.base.extension.observe
import com.alex3645.feature_personal_schedule.data.model.EventJson
import com.alex3645.feature_personal_schedule.presentation.personalScheduleView.recyclerView.EventRecyclerAdapter
import com.alex3645.feature_personal_schedule.databinding.FragmentPersonalEventsBinding
import com.alex3645.feature_personal_schedule.di.component.DaggerPersonalScheduleFragmentComponent
import com.alex3645.feature_personal_schedule.domain.model.Event
import kotlinx.coroutines.*
import javax.inject.Inject

class PersonalScheduleFragment : Fragment() {
    private val viewModel: PersonalScheduleViewModel by viewModels()

    private var _binding: FragmentPersonalEventsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var eventRecyclerAdapter: EventRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun injectDependency() {
        DaggerPersonalScheduleFragmentComponent.factory().create().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.stateLiveData, stateObserver)

        initRecycler()
        initView()
        initActions()
    }

    private fun initRecycler(){
        binding.eventRecyclerView.adapter = eventRecyclerAdapter
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun initView(){
        viewModel.loadPersonalEventsForUser()
    }

    private fun initActions(){
    }

    private val stateObserver = Observer<PersonalScheduleViewModel.ViewState> {

        //binding.swipeEventContainer.isVisible = it.isLoading
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }else{
            eventRecyclerAdapter.events = it?.events ?: listOf()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}