package com.alex3645.feature_search.presentation.searchView

import android.os.Bundle
import android.util.Log
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
import com.alex3645.feature_search.R
import com.alex3645.feature_search.databinding.FragmentSearchBinding
import com.alex3645.feature_search.di.component.DaggerSearchFragmentComponent
import com.alex3645.feature_search.presentation.searchView.recycler.SearchConferenceAdapter
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class SearchFragment: Fragment() {
    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

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
        setHasOptionsMenu(true)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    lateinit var searchView: SearchView
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search_upper_appbar_menu,menu)
        val menuItem = menu.findItem(R.id.searchViewItem)
        menuItem.expandActionView()
        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return false
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                findNavController().popBackStack()
                return false
            }
        })


        searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return considerAdapterCall(newText)
            }
        })
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
                Log.d("!!!", "test consider")
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

        initView()
    }

    private fun initView(){
        searchConferenceAdapter.setOnDebouncedClickListener {
            viewModel.navigateToConferenceDetail(findNavController(),it.id)
        }
        searchEventAdapter.setOnDebouncedClickListener {
            viewModel.navigateToConferenceDetail(findNavController(),it.conferenceId!!)
        }
        searchUserAdapter.setOnDebouncedClickListener {
            viewModel.navigateToUserAccount(findNavController(), it.id)
        }

        binding.searchRecyclerView.adapter = searchConferenceAdapter
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(activity)

        initActions()
    }

    private var tabPosition: Int = 0
    private fun initActions(){
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    searchConferenceAdapter.conferences = listOf()
                    searchEventAdapter.events = listOf()
                    searchUserAdapter.users = listOf()
                    Log.d("!!!", tab.tag.toString())
                    when (tab.position) {
                        0 -> {
                            binding.searchRecyclerView.adapter = searchConferenceAdapter
                            tabPosition = 0
                        }
                        1 -> {
                            binding.searchRecyclerView.adapter = searchEventAdapter
                            Log.d("!!!", "test tab")
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
}