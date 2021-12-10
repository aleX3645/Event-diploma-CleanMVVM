package com.alex3645.feature_account.presentation.settingsView

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.app.data.api.AppConstants
import com.alex3645.base.extension.observe
import com.alex3645.feature_account.di.component.DaggerAccountFragmentComponent
import com.alex3645.feature_account.presentation.settingsView.recyclerView.SettingsAdapter
import com.example.feature_account.R
import com.example.feature_account.databinding.FragmentSettingsBinding
import javax.inject.Inject

class SettingsFragment: Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var settingsAdapter: SettingsAdapter

    private lateinit var settingsList: List<String>

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
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.stateLiveData, stateObserver)

        initRecycler()
        initActions()
    }

    private fun initRecycler(){
        binding.settingsRecycler.adapter = settingsAdapter
        binding.settingsRecycler.layoutManager = LinearLayoutManager(activity)

        settingsList = listOf(context?.resources?.getString(R.string.settings) ?: "error",
            activity?.resources?.getString(R.string.create_link)?:"error",
            context?.resources?.getString(R.string.exit) ?: "error")
        settingsAdapter.settingsList = settingsList
    }

    private fun initActions(){
        binding.backButton.setOnClickListener {
            viewModel.navigateBack(findNavController())
        }

        settingsAdapter.setOnDebouncedClickListener {
            when(it){
                settingsList[0] -> {
                    viewModel.navigateToEdit(findNavController())
                }
                settingsList[1] -> {
                    context?.let{ it1 ->
                        val spManager = SharedPreferencesManager(it1)
                        val clipboard: ClipboardManager? =
                            ContextCompat.getSystemService(it1, ClipboardManager::class.java)
                        val clip = ClipData.newPlainText(it1.resources.getString(R.string.link_info),
                            AppConstants.INVITE_LINK_USER+spManager.fetchLogin())
                        clipboard?.setPrimaryClip(clip)

                        var toast = Toast.makeText(it1, it1.resources.getString(R.string.link_created), Toast.LENGTH_LONG)
                        toast.show()

                        viewModel.navigateBack(findNavController())
                    }
                }
                settingsList[2] -> {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}