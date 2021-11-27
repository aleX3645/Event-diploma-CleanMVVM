package com.alex3645.feature_conference_detail.presentation.settingsView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.feature_account.presentation.settingsView.recyclerView.SettingsAdapter
import com.alex3645.feature_conference_detail.R
import com.alex3645.feature_conference_detail.databinding.FragmentSettingsBinding
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailFragmentComponent
import javax.inject.Inject

class SettingsConferenceFragment: Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<SettingsConferenceFragmentArgs>()
    private val viewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var settingsAdapter: SettingsAdapter

    private var settingsList: List<String> = listOf()

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
        setHasOptionsMenu(true)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView(){
        viewModel.conferenceId = args.conferenceId.toLong()

        initRecycler()
        initActions()
    }

    private fun initRecycler(){
        binding.settingsRecycler.adapter = settingsAdapter
        binding.settingsRecycler.layoutManager = LinearLayoutManager(activity)

        settingsList = listOf(activity?.resources?.getString(R.string.statistics) ?: "error")
        settingsAdapter.settingsList = settingsList
    }

    private fun initActions(){
        binding.backButton.setOnClickListener {
            viewModel.navigateBack(findNavController())
        }

        settingsAdapter.setOnDebouncedClickListener {
            when(it){
                settingsList[0] -> {
                    viewModel.navigateToStats(findNavController())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}