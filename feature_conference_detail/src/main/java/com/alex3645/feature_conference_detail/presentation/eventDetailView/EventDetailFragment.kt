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
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_detail.R
import com.alex3645.feature_conference_detail.databinding.FragmentEventDetailBinding
import com.alex3645.feature_conference_detail.domain.model.Event
import com.alex3645.feature_conference_detail.domain.model.User
import java.text.SimpleDateFormat
import java.util.*

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

        val observer = Observer<User> {

            binding.organizerName.text = "${it.name} ${it.surname}"
            binding.shortInfoOrganizerTextBox.text = it.description
        }

        viewModel.organizer.observe(viewLifecycleOwner,observer)
        observe(viewModel.stateLiveData, stateObserver)

        viewModel.loadEvent(args.eventId, binding.organizerImage)
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

    private val beautyDateTimeFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    private fun initEvent(event: Event){
        this.event = event

        binding.eventTitle.text = if(this.event.name != "") this.event.name else context?.getString(R.string.no_data)?:""
        binding.eventStartDate.text = if(this.event.dateStart != "") beautyDateTimeFormatter.format(
            ServerConstants.serverDateTimeFormat.parse(this.event.dateStart)) else context?.getString(R.string.no_data)?:""
        binding.eventEndDate.text = if(this.event.dateEnd != "") beautyDateTimeFormatter.format(
            ServerConstants.serverDateTimeFormat.parse(this.event.dateEnd)) else context?.getString(R.string.no_data)?:""
        binding.eventDescription.text = if(this.event.description != "") this.event.description else context?.getString(R.string.no_data)?:""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}