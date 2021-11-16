package com.alex3645.feature_conference_detail.presentation.eventDetailView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_detail.databinding.FragmentEventDetailBinding
import com.alex3645.feature_conference_detail.domain.model.Event

class EventDetailFragment : Fragment() {
    private val viewModel: EventDetailViewModel by viewModels()

    private val args by navArgs<EventDetailFragmentArgs>()

    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.stateLiveData, stateObserver)

        viewModel.loadEventsForConference(args.eventId)
        initActions()
    }

    lateinit var event: Event

    private fun initActions(){
        binding.backButton.setOnClickListener {
            viewModel.navigateBack(findNavController())
        }

        binding.eventScheduleButton.setOnClickListener{
            viewModel.navigateToEvent(findNavController(),args.eventId)
        }
    }

    private val stateObserver = Observer<EventDetailViewModel.ViewState> {
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }else{
            initEvent(it.event!!)
        }
    }

    private fun initEvent(event: Event){
        this.event = event

        binding.eventTitle.text = this.event.name
        binding.eventStartDate.text = this.event.dateStart
        binding.eventEndDate.text = this.event.dateEnd
        binding.eventDescription.text = this.event.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}