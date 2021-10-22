package com.alex3645.feature_account.presentation.settingsView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.base.extension.observe
import com.alex3645.feature_account.di.component.DaggerAccountFragmentComponent
import com.alex3645.feature_account.presentation.settingsView.recyclerView.SettingsAdapter
import com.example.feature_account.databinding.FragmentSettingsBinding
import javax.inject.Inject

class SettingsFragment: Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var settingsAdapter: SettingsAdapter

    private val settingsList = listOf("Редактировать", "Выйти")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    private fun injectDependency() {
        DaggerAccountFragmentComponent.factory().create().inject(this)
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
        observe(viewModel.stateLiveData, stateObserver)

        initView()
    }

    private fun initView(){
        initRecycler()
        initActions()
    }

    private fun initRecycler(){
        binding.settingsRecycler.adapter = settingsAdapter
        binding.settingsRecycler.layoutManager = LinearLayoutManager(activity)

        settingsAdapter.settingsList = settingsList
    }

    private fun initActions(){
        settingsAdapter.setOnDebouncedClickListener {
            when(it){
                "Редактировать" -> {
                    viewModel.navigateToEdit(findNavController())
                }
                "Выйти" -> {
                    viewModel.removeUserData()
                }
            }
        }
    }

    private val stateObserver = Observer<SettingsViewModel.ViewState> {
        if(!it.isError){
            viewModel.navigateBack(findNavController())
        }
    }
}