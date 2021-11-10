package com.alex3645.feature_conference_detail.presentation.conferenceStatsView

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_detail.databinding.FragmentConferenceDetailBinding
import com.alex3645.feature_conference_detail.databinding.FragmentConferenceStatisticaBinding
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailFragmentComponent
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.presentation.conferenceStatsView.recyclerView.TariffStatsAdapter
import javax.inject.Inject

class ConferenceStatsFragment : Fragment(){
    private val viewModel: ConferenceStatsViewModel by viewModels()
    private val args by navArgs<ConferenceStatsFragmentArgs>()

    private var _binding: FragmentConferenceStatisticaBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var tariffStatsAdapter: TariffStatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConferenceStatisticaBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun injectDependency() {
        DaggerConferenceDetailFragmentComponent.factory().create().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.conferenceId = args.conferenceId

        observe(viewModel.stateLiveData, stateObserver)
        initView()
    }

    lateinit var conference: Conference
    private fun initView(){
        viewModel.loadConference()

        initRecycler()
    }

    private fun initRecycler(){

        binding.eventRecyclerView.adapter = tariffStatsAdapter
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private val stateObserver = Observer<ConferenceStatsViewModel.ViewState> {

        //binding.swipeEventContainer.isVisible = it.isLoading
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }else{
            tariffStatsAdapter.tariffs = it.conference?.tariffs?.toMutableList() ?: mutableListOf()
        }
    }
}