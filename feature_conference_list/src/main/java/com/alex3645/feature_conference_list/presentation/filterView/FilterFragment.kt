package com.alex3645.feature_conference_list.presentation.filterView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alex3645.feature_event_list.R
import com.alex3645.feature_event_list.databinding.FragmentFilterBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import java.util.*

class FilterFragment : Fragment(){

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<FilterFragmentArgs>()
    val viewModel: FilterViewModel by viewModels()

    private lateinit var places: PlacesClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initActions()
    }

    private fun initView(){
        args.filterList.forEach {
            setSelectedId(it)
        }


        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this.isEnabled = false
                viewModel.filterList = getSelectedIds()
                viewModel.city = binding.cityTextInput.editText?.text.toString()
                viewModel.navigateBack(findNavController())
            }
        })

        if (!Places.isInitialized()) {
            Places.initialize(this.requireContext(), resources.getString(R.string.google_maps_key), Locale.getDefault())
        }

        places = Places.createClient(this.requireContext())
    }

    private var settledFlag = false
    private fun initActions(){
        binding.editTextAuto.addTextChangedListener{ editable ->

            viewModel.getPredictionAdapter(editable.toString(), callback = {
                (binding.cityTextInput.editText as? AutoCompleteTextView)?.setAdapter(it)
                if(!settledFlag){
                    (binding.cityTextInput.editText as? AutoCompleteTextView)?.showDropDown()
                }else{
                    settledFlag = false
                }
            })
        }

        binding.backButton.setOnClickListener {
            viewModel.filterList = getSelectedIds()
            viewModel.city = binding.cityTextInput.editText?.text.toString()
            viewModel.navigateBack(findNavController())
        }

        binding.resetButton.setOnClickListener {
            viewModel.resetValues()
            viewModel.navigateBack(findNavController())
        }

        (binding.cityTextInput.editText as? AutoCompleteTextView)?.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, _ -> settledFlag = true }
    }

    fun getSelectedIds() : MutableList<Int>{
        val idList = mutableListOf<Int>()

        if(binding.chipNoCategory.isChecked){
            idList.add(0)
        }
        if(binding.chipPolitics.isChecked){
            idList.add(1)
        }
        if(binding.chipSociety.isChecked){
            idList.add(2)
        }
        if(binding.chipEconomic.isChecked){
            idList.add(3)
        }
        if(binding.chipSport.isChecked){
            idList.add(4)
        }
        if(binding.chipCulture.isChecked){
            idList.add(5)
        }
        if(binding.chipTech.isChecked){
            idList.add(6)
        }
        if(binding.chipScience.isChecked){
            idList.add(7)
        }
        if(binding.chipAuto.isChecked){
            idList.add(8)
        }
        if(binding.chipOther.isChecked){
            idList.add(9)
        }
        return idList
    }

    private fun setSelectedId(id: Int){
        when(id){
            0-> binding.chipNoCategory.isChecked = true
            1-> binding.chipPolitics.isChecked = true
            2-> binding.chipSociety.isChecked = true
            3-> binding.chipEconomic.isChecked = true
            4-> binding.chipSport.isChecked = true
            5-> binding.chipCulture.isChecked = true
            6-> binding.chipTech.isChecked = true
            7-> binding.chipScience.isChecked = true
            8-> binding.chipAuto.isChecked = true
            9-> binding.chipOther.isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}