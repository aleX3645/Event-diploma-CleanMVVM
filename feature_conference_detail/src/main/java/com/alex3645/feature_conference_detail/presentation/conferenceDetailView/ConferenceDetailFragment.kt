package com.alex3645.feature_conference_detail.presentation.conferenceDetailView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_detail.databinding.FragmentConferenceDetailBinding
import com.alex3645.feature_conference_detail.domain.model.Conference

class ConferenceDetailFragment(): Fragment() {

    private val viewModel: ConferenceDetailViewModel by viewModels()

    private var conferenceId: Int? = null
    constructor(id: Int) : this() {
        conferenceId = id
    }

    private var _binding: FragmentConferenceDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConferenceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        conferenceId?.let{viewModel.conferenceId = it}

        observe(viewModel.stateLiveData, stateObserver)
        initView()
    }

    lateinit var conference: Conference
    private fun initView(){
        viewModel.loadConference()

        initActions()
    }

    private val stateObserver = Observer<ConferenceDetailViewModel.ViewState> {

        binding.conferenceProgressBar.isVisible = it.isLoading
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }else{
            initConference(it.conference!!)
        }
    }

    private fun initConference(conference: Conference){
        this.conference = conference

        binding.conferenceTitle.text = this.conference.name
        binding.conferenceStartDate.text = this.conference.dateStart
        binding.conferenceEndDate.text = this.conference.dateEnd
        binding.conferencePlace.text = this.conference.location
        binding.conferenceCategory.text = this.conference.category.toString()
        binding.conferenceDescription.text = this.conference.description
    }

    private fun initActions(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}