package com.alex3645.feature_conference_detail.presentation.tariffListView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_detail.databinding.FragmentTariffRecyclerBinding
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailFragmentComponent
import com.alex3645.feature_conference_detail.presentation.tariffListView.recyclerView.TariffAdapter
import javax.inject.Inject

class TariffListFragment : Fragment() {

    private val viewModel:TariffListViewModel by viewModels()
    private val args by navArgs<TariffListFragmentArgs>()

    private var _binding: FragmentTariffRecyclerBinding? = null
    private val binding get() = _binding!!


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
        _binding = FragmentTariffRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.stateLiveData, stateObserver)

        initView()
        initRecycler()
        initActions()
    }

    private fun initView(){
        context?.let { tariffAdapter = TariffAdapter(it) }
        viewModel.conferenceId = args.conferenceId
        viewModel.loadTariffs()
    }

    private fun initRecycler(){
        tariffAdapter.setOnDebouncedClickListener {
            viewModel.registerTicket(it.id.toLong(),findNavController())
        }

        binding.eventRecyclerView.adapter = tariffAdapter
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun initActions(){
        binding.backButton.setOnClickListener {
            viewModel.navigateBack(findNavController())
        }
    }

    private val stateObserver = Observer<TariffListViewModel.ViewState> {

        //binding.swipeEventContainer.isRefreshing = it.isLoading
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }else{
            tariffAdapter.tariffs = it.tariffs?.toMutableList() ?: mutableListOf()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}