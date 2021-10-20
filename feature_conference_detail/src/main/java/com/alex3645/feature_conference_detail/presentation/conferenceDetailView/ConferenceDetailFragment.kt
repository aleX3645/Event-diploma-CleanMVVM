package com.alex3645.feature_conference_detail.presentation.conferenceDetailView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alex3645.feature_conference_detail.databinding.FragmentConferenceDetailBinding
import com.alex3645.feature_conference_detail.domain.model.Conference

class ConferenceDetailFragment: Fragment() {

    private val viewModel: ConferenceDetailViewModel by viewModels()

    private val args by navArgs<ConferenceDetailFragmentArgs>()

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

        initView()
    }

    lateinit var conference: Conference
    private fun initView(){
        //conference = null
        Log.d("test", args.conferenceId.toString())
        return
        binding.conferenceTitle.text = conference.name
        binding.conferenceStartDate.text = conference.dateStart
        binding.conferenceEndDate.text = conference.dateEnd
        binding.conferencePlace.text = conference.location
        binding.conferenceCategory.text = conference.category.toString()
        binding.conferenceDescription.text = conference.description
        binding.conferenceProgressBar.isVisible = false

        initActions()
    }

    private fun initActions(){
        binding.scheduleButton.setOnClickListener{
            viewModel.navigateToEventList(findNavController(),conference)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}