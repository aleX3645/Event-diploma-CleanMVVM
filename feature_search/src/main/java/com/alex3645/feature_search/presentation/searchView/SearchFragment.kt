package com.alex3645.feature_search.presentation.searchView

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.base.extension.observe
import com.alex3645.feature_search.presentation.searchView.recycler.SearchEventAdapter
import com.alex3645.feature_search.presentation.searchView.recycler.SearchUserAdapter
import com.alex3645.feature_search.databinding.FragmentSearchBinding
import com.alex3645.feature_search.di.component.DaggerSearchFragmentComponent
import com.alex3645.feature_search.presentation.searchView.recycler.SearchConferenceAdapter
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class SearchFragment: Fragment() {
    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    lateinit var searchView: SearchView

    @Inject
    lateinit var searchConferenceAdapter: SearchConferenceAdapter
    @Inject
    lateinit var searchEventAdapter: SearchEventAdapter
    @Inject
    lateinit var searchUserAdapter: SearchUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    private fun injectDependency() {
        DaggerSearchFragmentComponent.factory().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun considerAdapterCall(text: String?) : Boolean{
        if(text== null){
            return false
        }

        if(text == ""){
            searchConferenceAdapter.conferences = listOf()
            searchEventAdapter.events = listOf()
            searchUserAdapter.users = listOf()
            return true
        }

        when(tabPosition){
            0 -> {
                viewModel.searchConference(text)
                return true
            }
            1 -> {
                viewModel.searchEvents(text)
                return true
            }
            2 -> {
                viewModel.searchUsers(text)
                return true
            }
        }

        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.stateLiveData, stateObserver)

        initActions()
        initView()
    }

    private fun initView(){
        binding.searchRecyclerView.adapter = searchConferenceAdapter
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private var tabPosition: Int = 0
    private fun initActions(){
        binding.backButton.setOnClickListener {
            viewModel.navigateBack(findNavController())
        }

        searchConferenceAdapter.setOnDebouncedClickListener {
            viewModel.navigateToConferenceDetail(findNavController(),it.id)
        }
        searchEventAdapter.setOnDebouncedClickListener {
            viewModel.navigateToConferenceDetail(findNavController(),it.conferenceId)
        }
        searchUserAdapter.setOnDebouncedClickListener {
            viewModel.navigateToUserAccount(findNavController(), it.id)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    searchConferenceAdapter.conferences = listOf()
                    searchEventAdapter.events = listOf()
                    searchUserAdapter.users = listOf()

                    when (tab.position) {
                        0 -> {
                            binding.searchRecyclerView.adapter = searchConferenceAdapter
                            tabPosition = 0
                        }
                        1 -> {
                            binding.searchRecyclerView.adapter = searchEventAdapter
                            tabPosition = 1
                        }
                        2 -> {
                            binding.searchRecyclerView.adapter = searchUserAdapter
                            tabPosition = 2
                        }
                    }
                    considerAdapterCall(searchView.query.toString())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // nothing
            }
        })

        searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return considerAdapterCall(newText)
            }
        })
    }


    private val stateObserver = Observer<SearchViewModel.ViewState> {
        if(!it.isError){
            when (tabPosition) {
                0 -> {
                    searchConferenceAdapter.conferences = it.conferences
                }
                1 -> {
                    searchEventAdapter.events = it.events
                }
                2 -> {
                    searchUserAdapter.users = it.users
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}