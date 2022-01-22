package com.alex3645.feature_conference_builder.presentation.tariffEditorView

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alex3645.feature_conference_builder.databinding.FragmentTariffEditorBinding
import com.alex3645.feature_conference_builder.domain.model.Tariff

class TariffEditorFragment: Fragment() {

    private var _binding: FragmentTariffEditorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TariffEditorViewModel by viewModels()
    private val args by navArgs<TariffEditorFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTariffEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initActions()
    }

    private fun initView(){
        args.conference?.let {
            viewModel.conference = it
        }

        if(args.editableTariffId > 0){
            viewModel.conference?.tariffs?.let { it1 -> initViewElements(it1.first { it -> it.id == args.editableTariffId }) }
        }
    }

    private fun initViewElements(tariff: Tariff){


        binding.ticketsInputText.text = Editable.Factory().newEditable(tariff.ticketsTotal.toString())
        binding.costInputText.text = Editable.Factory().newEditable(tariff.cost.toString())
        binding.nameInputText.text = Editable.Factory().newEditable(tariff.name)
    }

    private fun initActions(){
        binding.backButton.setOnClickListener {
            viewModel.navigateBack(findNavController())
        }

        binding.saveButton.setOnClickListener {
            viewModel.tariff = Tariff(
                id = if(args.editableTariffId<=0) 0 else args.editableTariffId,
                conferenceId = viewModel.conference?.id?: 0,
                cost = binding.costTextField.editText?.text.toString().toDouble(),
                name = binding.nameTextField.editText?.text.toString(),
                ticketsLeft = 0,
                ticketsTotal = binding.ticketsTextField.editText?.text.toString().toInt()
            )

            viewModel.saveTariff()
            viewModel.navigateBack(findNavController())
        }
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.navigateBack(findNavController())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}