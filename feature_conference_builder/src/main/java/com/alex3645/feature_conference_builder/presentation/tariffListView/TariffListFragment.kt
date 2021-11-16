package com.alex3645.feature_conference_builder.presentation.tariffListView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.feature_conference_builder.databinding.FragmentTariffListBinding
import com.alex3645.feature_conference_builder.di.components.DaggerFragmentBuilderComponent
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.presentation.tariffListView.recyclerView.TariffAdapter
import javax.inject.Inject

class TariffListFragment : Fragment(){

    private var _binding: FragmentTariffListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TariffListViewModel by viewModels()
    private val args by navArgs<TariffListFragmentArgs>()

    @Inject
    lateinit var tariffAdapter: TariffAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    private fun injectDependency() {
        DaggerFragmentBuilderComponent.factory().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTariffListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView(){
        args.conference?.let {
            viewModel.conference = it
        }

        tariffAdapter.tariffs = viewModel.conference?.tariffs ?: mutableListOf()

        initBackStackObserver()
        initActions()
        initRecycler()
    }

    private fun initRecycler(){
        binding.recyclerView.adapter = tariffAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun initBackStackObserver(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Conference>("conference")
            ?.observe(viewLifecycleOwner) {
                viewModel.conference = it
                tariffAdapter.tariffs = it.tariffs ?: mutableListOf()
            }
    }

    private fun initActions(){
        binding.backButton.setOnClickListener {
            viewModel.navigateBack(findNavController())
        }

        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this.isEnabled = false
                viewModel.navigateBack(findNavController())
            }
        })

        binding.tariffFloatingActionButton.setOnClickListener {
            viewModel.navigateToTariffEditor(findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}