package com.alex3645.feature_conference_detail.presentation.tariffListView

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_detail.databinding.EventRecyclerListBinding
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailFragmentComponent
import com.alex3645.feature_conference_detail.domain.model.Tariff
import com.alex3645.feature_conference_detail.presentation.conferenceDetailView.ConferenceDetailViewModel
import com.alex3645.feature_conference_detail.presentation.eventDetailView.EventDetailFragmentArgs
import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.EventRecyclerViewModel
import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.recyclerView.EventRecyclerAdapter
import com.alex3645.feature_conference_detail.presentation.tariffListView.recyclerView.TariffAdapter
import javax.inject.Inject

class TariffListFragment : Fragment() {

    private val viewModel:TariffListViewModel by viewModels()
    private val args by navArgs<TariffListFragmentArgs>()

    private var _binding: EventRecyclerListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var tariffAdapter: TariffAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.stateLiveData, stateObserver)

        initView()
    }

    private fun initView(){
        viewModel.conferenceId = args.conferenceId
        viewModel.loadTariffs()

        initRecycler()
    }

    private fun initRecycler(){
        tariffAdapter.setOnDebouncedClickListener {
            Log.d("!!!", "click")
            viewModel.registerTicket(it.id.toLong())
        }

        binding.eventRecyclerView.adapter = tariffAdapter
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private val stateObserver = Observer<TariffListViewModel.ViewState> {

        binding.swipeEventContainer.isRefreshing = it.isLoading
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }else{
            tariffAdapter.tariffs = it.tariffs?.toMutableList() ?: mutableListOf()
        }
    }
}