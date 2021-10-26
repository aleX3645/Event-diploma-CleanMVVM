package com.alex3645.feature_conference_builder.presentation.conferenceEditorView

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_builder.R
import com.alex3645.feature_conference_builder.databinding.FragmentConferenceEditorBinding
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/*
* NO_CATEGORY(0, "No category"),
    POLITICS(1, "Politics"),
    SOCIETY(2, "Society"),
    ECONOMICS(3, "Economics"),
    SPORTS(4, "Sport"),
    CULTURE(5, "Culture"),
    TECHNOLOGIES(6, "Tech"),
    SCIENCE(7, "Science"),
    AUTO(8, "Auto"),
    OTHERS(9, "Others");
* */

class ConferenceEditorFragment : Fragment() {
    private val menuItems = listOf("No category", "Society", "Economics", "Sport", "Culture", "Tech", "Science", "Auto", "Others")

    private val viewModel: ConferenceEditorViewModel by viewModels()

    private var _binding: FragmentConferenceEditorBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConferenceEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.stateLiveData, stateObserver)

        initView()
    }

    private fun initView(){

        initBackStackObserver()
        initActions()
    }

    private fun initBackStackObserver(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Conference>("conference")
            ?.observe(viewLifecycleOwner) {
            Log.d("conf", it.name ?: "error")
        }
    }

    private fun initActions(){

        setStartDateTimeActions()
        setEndDateTimeActions()

        setButtons()

        setDropDownMenu()
    }

    private fun setButtons(){
        binding.addEvents.setOnClickListener {
            if(beforeNextStepCheck()){
                setConference()
                viewModel.navigateToEventListEditor(findNavController())
            }
        }

        binding.saveButton.setOnClickListener {
            if(beforeNextStepCheck()){
                setConference()
                viewModel.saveConference()
            }
        }
    }

    private fun setConference(){
        viewModel.conference.name = binding.nameInputText.text.toString()
        viewModel.conference.description = binding.descriptionInputText.text.toString()
        viewModel.conference.location = binding.locationInputText.text.toString()
        viewModel.conference.category = if (binding.menuInputText.listSelection == -1) 0 else binding.menuInputText.listSelection
        viewModel.conference.organizerId = 0

        viewModel.conference.dateStart = simpleDateFormatServer.format(startDate.time).toString()
        viewModel.conference.dateEnd = simpleDateFormatServer.format(endDate.time).toString()
    }

    private fun beforeNextStepCheck() : Boolean{
        if(binding.endDateInputText.text.contentEquals("")){
            binding.endDateTextField.helperText = "Для продолжения задайте значение данного поля"
            return false
        }
        binding.endDateTextField.helperText = ""

        if(binding.startDateInputText.text.contentEquals("")){
            binding.startDateTextField.helperText = "Для продолжения задайте значение данного поля"
            return false
        }
        binding.startDateTextField.helperText = ""

        if(binding.startTimeInputText.text.contentEquals("")){
            binding.startTimeTextField.helperText = "Для продолжения задайте значение данного поля"
            return false
        }
        binding.startTimeTextField.helperText = ""

        if(binding.endTimeInputText.text.contentEquals("")){
            binding.endTimeTextField.helperText = "Для продолжения задайте значение данного поля"
            return false
        }
        binding.endTimeTextField.helperText = ""

        if(binding.nameInputText.text.contentEquals("")){
            binding.conferenceNameTextField.helperText = "Для продолжения задайте значение данного поля"
            return false
        }

        binding.conferenceNameTextField.helperText = ""


        return correctDateTimeCheck(startDate, endDate)
    }

    private fun setDropDownMenu(){
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, menuItems)
        (binding.exposedMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun getTimePickerByTime(hour: Int) : MaterialTimePicker{
        return MaterialTimePicker.Builder()
                .setInputMode(INPUT_MODE_KEYBOARD)
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(0)
                .build()
    }

    private fun getDatePickerByDate(dateMillis: Long) : MaterialDatePicker<Long>{
        return MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(
                    CalendarConstraints.Builder().setValidator(
                        DateValidatorPointForward.from(dateMillis-dayMilliseconds)
                    ).build()
                ).build()
    }

    private var startDate: Calendar = Calendar.getInstance(Locale.getDefault())
    private var endDate: Calendar = Calendar.getInstance(Locale.getDefault())
    private val dayMilliseconds = TimeUnit.DAYS.toMillis(1)
    private val simpleDateFormatServer = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",Locale.getDefault())
    private val simpleDateFormatClientDate = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
    private val simpleDateFormatClientTime = SimpleDateFormat("HH:mm",Locale.getDefault())

    private fun setStartDateTimeActions(){
        binding.startDateInputText.setOnClickListener {

            val datePicker = getDatePickerByDate(Calendar.getInstance(Locale.getDefault()).timeInMillis)

            datePicker.addOnPositiveButtonClickListener {
                val temp = Calendar.getInstance(Locale.getDefault())
                temp.timeInMillis = it

                startDate.set(Calendar.YEAR, temp.get(Calendar.YEAR))
                startDate.set(Calendar.MONTH, temp.get(Calendar.MONTH))
                startDate.set(Calendar.DATE,  temp.get(Calendar.DATE))

                binding.startDateTextField.editText?.text = Editable.Factory.getInstance().newEditable(
                    simpleDateFormatClientDate.format(startDate.time).toString()
                )

                correctDateTimeCheck(startDate,endDate)
            }

            datePicker.show(parentFragmentManager, "datePicker")
        }

        binding.startTimeInputText.setOnClickListener {
            val timePicker = getTimePickerByTime(9)

            timePicker.addOnPositiveButtonClickListener {
                startDate.set(Calendar.HOUR, timePicker.hour)
                startDate.set(Calendar.MINUTE, timePicker.minute)

                binding.startTimeTextField.editText?.text = Editable.Factory.getInstance().newEditable(
                    simpleDateFormatClientTime.format(startDate.time).toString()
                )

                correctDateTimeCheck(startDate,endDate)
            }

            timePicker.show(parentFragmentManager, "timePicker")
        }
    }

    private fun setEndDateTimeActions(){
        binding.endDateInputText.setOnClickListener {

            val datePicker = getDatePickerByDate(startDate.timeInMillis)

            datePicker.addOnPositiveButtonClickListener {
                val temp = Calendar.getInstance(Locale.getDefault())
                temp.timeInMillis = it

                endDate.set(Calendar.YEAR, temp.get(Calendar.YEAR))
                endDate.set(Calendar.MONTH, temp.get(Calendar.MONTH))
                endDate.set(Calendar.DATE,  temp.get(Calendar.DATE))

                binding.endDateTextField.editText?.text = Editable.Factory.getInstance().newEditable(
                    simpleDateFormatClientDate.format(endDate.time).toString()
                )

                correctDateTimeCheck(startDate,endDate)
            }

            datePicker.show(parentFragmentManager, "datePicker")
        }

        binding.endTimeInputText.setOnClickListener {
            val timePicker = getTimePickerByTime(18)

            timePicker.addOnPositiveButtonClickListener {
                endDate.set(Calendar.HOUR, timePicker.hour)
                endDate.set(Calendar.MINUTE, timePicker.minute)

                binding.endTimeTextField.editText?.text = Editable.Factory.getInstance().newEditable(
                    simpleDateFormatClientTime.format(endDate.time).toString()
                )

                correctDateTimeCheck(startDate,endDate)
            }

            timePicker.show(parentFragmentManager, "timePicker")
        }
    }

    private fun correctDateTimeCheck(startDate: Calendar, endDate: Calendar) : Boolean{

        binding.endDateTextField.helperText = ""
        binding.endTimeTextField.helperText = ""

        if(firstDateOlder(startDate, endDate) && dateSettledFlag()){
            binding.endDateTextField.helperText = "Дата начала конференции не может быть раньше даты окончания"
            return false
        }
        if(endDate<=startDate && dateSettledFlag() && timeSettledFlag()){
            binding.endTimeTextField.helperText = "Момент начала конференции должен быть позднее, чем момент окончания"
            return false
        }

        return true
    }

    private fun firstDateOlder(firstDate: Calendar, secondDate: Calendar) : Boolean{
        return (firstDate.get(Calendar.YEAR) >= secondDate.get(Calendar.YEAR)
                && firstDate.get(Calendar.MONTH) >= secondDate.get(Calendar.MONTH)
                && firstDate.get(Calendar.DATE) > secondDate.get(Calendar.DATE))
    }

    private fun dateSettledFlag() : Boolean{
        return (!binding.endDateTextField.editText?.text.contentEquals("")
                && !binding.endDateTextField.editText?.text.contentEquals(""))
    }

    private fun timeSettledFlag() : Boolean{
        return (!binding.endTimeTextField.editText?.text.contentEquals("")
                && !binding.endTimeTextField.editText?.text.contentEquals(""))
    }

    private val stateObserver = Observer<ConferenceEditorViewModel.ViewState> {

        binding.registrationProgressBar.isVisible = it.isLoading
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }else{
            findNavController().popBackStack()
        }
    }
}