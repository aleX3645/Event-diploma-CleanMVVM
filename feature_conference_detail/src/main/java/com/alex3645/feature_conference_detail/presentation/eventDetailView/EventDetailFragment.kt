package com.alex3645.feature_conference_detail.presentation.eventDetailView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.alex3645.feature_conference_list.domain.model.Event
import com.alex3645.feature_event_list.databinding.EventDetailBinding

class EventDetailFragment : Fragment() {
    private val viewModel: EventDetailViewModel by viewModels()

    private val args by navArgs<EventDetailFragmentArgs>()

    private var _binding: EventDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    lateinit var event: Event
    private fun initView(){
        event = args.event

        binding.eventTitle.text = event.name
        binding.eventStartDate.text = event.dateStart
        binding.eventEndDate.text = event.dateEnd
        binding.eventDescription.text = event.description

        initActions()
    }

    private fun initActions(){
        binding.eventScheduleButton.setOnClickListener{
            //viewModel.navigateToEventList(findNavController(),args.conference)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}