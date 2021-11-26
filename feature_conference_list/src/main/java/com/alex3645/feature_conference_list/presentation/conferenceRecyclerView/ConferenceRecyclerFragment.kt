package com.alex3645.feature_conference_list.presentation.conferenceRecyclerView

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.app.presentation.util.infoDialogView.InfoDialogFragment
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_list.di.component.DaggerConferenceListFragmentComponent
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.recyclerView.ConferenceAdapter
import com.alex3645.feature_event_list.R
import com.alex3645.feature_event_list.databinding.FragmentRecyclerListBinding
import javax.inject.Inject


class ConferenceRecyclerFragment: Fragment() {

    private var _binding: FragmentRecyclerListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ConferenceRecyclerViewModel by viewModels()

    @Inject
    lateinit var conferenceAdapter: ConferenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    private fun injectDependency() {
        DaggerConferenceListFragmentComponent.factory().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.stateLiveData, stateObserver)

        initRecycler()
        initActions()
        initView()
    }

    private fun initView(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<IntArray>("filter")
            ?.observe(viewLifecycleOwner) {
                viewModel.filterList = it.toMutableList()
            }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("city")
            ?.observe(viewLifecycleOwner) {
                viewModel.city = it
            }

        context?.let {
            binding.floatingActionButton.isVisible = viewModel.isUserOrganizer(it)
        }

        binding.swipeContainer.isRefreshing = true
        viewModel.loadData()
    }

    private fun initRecycler(){
        binding.recyclerView.adapter = conferenceAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun initActions(){
        binding.searchButton.setOnClickListener {
            viewModel.navigateToSearch(findNavController())
        }

        binding.swipeContainer.setOnRefreshListener {
            viewModel.loadDataFromStart()
        }

        val navController = findNavController()

        binding.filtersButton.setOnClickListener {
            viewModel.navigateToFilter(navController)
        }

        binding.floatingActionButton.setOnClickListener {
            viewModel.navigateToConferenceBuilder(navController)
        }

        conferenceAdapter.setOnDebouncedClickListener {
            viewModel.navigateToConferenceDetail(navController,it)
        }
    }

    private val stateObserver = Observer<ConferenceRecyclerViewModel.ViewState> {

        binding.swipeContainer.isRefreshing = it.isLoading
        if(it.isError){
            val infoDialog = InfoDialogFragment()
            infoDialog.show(parentFragmentManager,"infoFragment","", "")
        }else{
            conferenceAdapter.conferences = it.conferences
            conferenceAdapter.filter(viewModel.filterList, viewModel.city)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}