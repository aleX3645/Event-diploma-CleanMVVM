package com.alex3645.feature_conference_list.presentation.conferenceRecyclerView

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_list.di.component.DaggerFragmentComponent
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.recyclerView.ConferenceAdapter
import com.alex3645.feature_event_list.R
import com.alex3645.feature_event_list.databinding.FragmentRecyclerListBinding
import javax.inject.Inject

class ConferenceRecyclerFragment: Fragment() {

    private var _binding: FragmentRecyclerListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var conferenceAdapter: ConferenceAdapter

    private val viewModel: ConferenceRecyclerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    private fun injectDependency() {
        DaggerFragmentComponent.factory().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentRecyclerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.conference_upper_appbar_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.auth -> {
                viewModel.navigateToAccountAuth(findNavController())
                true
            }
            R.id.search -> {
                viewModel.navigateToSearch(findNavController())
                true
            }
            else -> false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        observe(viewModel.stateLiveData, stateObserver)

        binding.swipeContainer.isRefreshing = true
        viewModel.loadData()
    }

    private  fun initRecycler(){
        initActions()

        binding.recyclerView.adapter = conferenceAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun initActions(){
        binding.swipeContainer.setOnRefreshListener {
            viewModel.loadDataFromStart()
        }

        conferenceAdapter.setOnDebouncedClickListener {
            viewModel.navigateToConferenceDetail(findNavController(),it)
        }
    }

    private val stateObserver = Observer<ConferenceRecyclerViewModel.ViewState> {
        conferenceAdapter.conferences = it.conferences

        binding.swipeContainer.isRefreshing = it.isLoading
        if(it.isError){
            Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}