package com.alex3645.feature_ticket_list.presentation.ticketListView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.base.extension.observe
import com.alex3645.feature_ticket_list.di.component.DaggerTicketListFragmentComponent
import com.alex3645.feature_ticket_list.presentation.ticketListView.recyclerView.TicketListAdapter
import com.alex3645.feature_ticket_list.databinding.FragmentTicketListBinding
import javax.inject.Inject

class TicketListFragment: Fragment() {
    private val viewModel: TicketListViewModel by viewModels()

    private var _binding: FragmentTicketListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var ticketRecyclerAdapter: TicketListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun injectDependency() {
        DaggerTicketListFragmentComponent.factory().create().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.stateLiveData, stateObserver)

        initRecycler()
        initView()
        initActions()
    }

    private fun initRecycler(){
        binding.eventRecyclerView.adapter = ticketRecyclerAdapter
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun initView(){
        viewModel.loadTickets()
    }

    private fun initActions(){
    }

    private val stateObserver = Observer<TicketListViewModel.ViewState> {

        //binding.swipeEventContainer.isVisible = it.isLoading
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }else{
            ticketRecyclerAdapter.tickets = it?.tickets ?: listOf()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}