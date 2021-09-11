package com.alex3645.feature_conference_list.presentation.conferenceRecyclerView

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
import androidx.navigation.navGraphViewModels
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
        _binding = FragmentRecyclerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        conferenceAdapter.setOnDebouncedClickListener {
            //
        }

        binding.recyclerView.adapter = conferenceAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        //Toast.makeText(context, "error1", Toast.LENGTH_LONG).show()
        observe(viewModel.stateLiveData, stateObserver)

        binding.progressBar.isVisible = true
        viewModel.loadData()
    }

    private val stateObserver = Observer<ConferenceRecyclerViewModel.ViewState> {
        conferenceAdapter.conferences = it.conferences

        binding.progressBar.isVisible = it.isLoading
        if(it.isError){
            Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}