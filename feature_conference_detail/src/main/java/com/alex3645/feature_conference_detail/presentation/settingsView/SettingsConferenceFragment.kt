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
import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alex3645.app.data.api.AppConstants
import com.alex3645.base.extension.observe


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

        observe(viewModel.stateLiveData, stateObserver)

        initMenuValues()
        initRecycler()
        initActions()
    }

    private lateinit var menu: List<String>
    private fun initMenuValues(){
        menu = listOf(activity?.resources?.getString(R.string.statistics) ?: "error",
            activity?.resources?.getString(R.string.create_invite_link)?:"error",
            activity?.resources?.getString(R.string.edit_conference)?:"error")
    }

    private fun initRecycler(){
        binding.settingsRecycler.adapter = settingsAdapter
        binding.settingsRecycler.layoutManager = LinearLayoutManager(activity)
        viewModel.determineMenuType()
    }

    private fun initActions(){
        binding.backButton.setOnClickListener {
            viewModel.navigateBack(findNavController())
        }

        settingsAdapter.setOnDebouncedClickListener {
            when(it){
                menu[0] -> {
                    viewModel.navigateToStats(findNavController())
                }
                menu[1]->{
                    context?.let{ it1 ->
                        val clipboard: ClipboardManager? = getSystemService(it1, ClipboardManager::class.java)
                        val clip = ClipData.newPlainText(it1.resources.getString(R.string.link_info),
                            AppConstants.INVITE_LINK_CONFERENCE+args.conferenceId)
                        clipboard?.setPrimaryClip(clip)
                        Toast.makeText(context, it1.resources.getString(R.string.link_created), Toast.LENGTH_LONG).show()
                        viewModel.navigateBack(findNavController())
                    }
                }
                menu[2]->{
                    viewModel.navigateToBuilder(findNavController())
                }
            }
        }
    }

    private val stateObserver = Observer<SettingsViewModel.ViewState> {
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }else{
            settingsList = if(it.userOrganizer){
                listOf(menu[0], menu[1],menu[2])
            }else{
                listOf(menu[1])
            }
            settingsAdapter.settingsList = settingsList
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}