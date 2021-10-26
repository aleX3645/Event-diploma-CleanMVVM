package com.alex3645.feature_conference_builder.presentation.eventEditorView

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alex3645.feature_conference_builder.databinding.FragmentEventEditorBinding
import com.alex3645.feature_conference_builder.domain.model.Event
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class EventEditorFragment : Fragment() {
    private val viewModel: EventEditorViewModel by viewModels()

    private val args by navArgs<EventEditorFragmentArgs>()

    private var _binding: FragmentEventEditorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private var globalDateStart: Calendar = Calendar.getInstance()
    private var globalDateEnd: Calendar = Calendar.getInstance()

    private val simpleDateFormatServer = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",Locale.getDefault())

    private fun initView(){
        var conference = args.conference
        conference?.let {
            viewModel.conference = it
            globalDateStart.time = simpleDateFormatServer.parse(it!!.dateStart)
            globalDateEnd.time = simpleDateFormatServer.parse(it!!.dateEnd)
        }



        args.event?.let{
            viewModel.event = it
            globalDateStart.time = simpleDateFormatServer.parse(it!!.dateStart)
            globalDateEnd.time = simpleDateFormatServer.parse(it!!.dateEnd)
        }

        initBackStackObserver()
        initActions()
    }

    private fun initBackStackObserver(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Event>("event")
            ?.observe(viewLifecycleOwner) {
                viewModel.newEvent = it
            }
    }

    private fun initActions(){
        setStartDateTimeActions()
        setEndDateTimeActions()

        setButtons()
    }

    private fun setButtons(){
        binding.addEvents.setOnClickListener {
            if(save()){
                viewModel.navigateToEventListEditor(findNavController())
            }
        }

        binding.saveButton.setOnClickListener {
            if(save()){
                viewModel.navigateBack(findNavController())
            }
        }
    }

    private fun save() : Boolean{
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
            binding.eventNameTextField.helperText = "Для продолжения задайте значение данного поля"
            return false
        }
        binding.eventNameTextField.helperText = ""


        if(correctDateTimeCheck(startDate, endDate)){
            viewModel.buildNewEvent(
                binding.nameInputText.text.toString(),
                globalDateStart,
                globalDateEnd,
                binding.descriptionInputText.text.toString())

            return true
        }

        return false
    }

    private fun getTimePickerByTime(hour: Int) : MaterialTimePicker {
        return MaterialTimePicker.Builder()
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(hour)
            .setMinute(0)
            .build()
    }

    private fun getDatePickerByDate(dateMillis: Long) : MaterialDatePicker<Long> {
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

    val simpleDateFormatClientDate = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
    val simpleDateFormatClientTime = SimpleDateFormat("HH:mm",Locale.getDefault())

    private fun setStartDateTimeActions(){
        binding.startDateInputText.setOnClickListener {

            val datePicker = getDatePickerByDate(globalDateStart.timeInMillis)

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

            val datePicker = getDatePickerByDate(globalDateStart.timeInMillis)

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
        binding.startTimeTextField.helperText = ""
        binding.endTimeTextField.helperText = ""

        if(firstDateOlder(startDate, endDate) && dateSettledFlag()){
            binding.endDateTextField.helperText = "Дата начала конференции не может быть раньше даты окончания"
            return false
        }
        if(endDate<=startDate && dateSettledFlag() && timeSettledFlag()){
            binding.endTimeTextField.helperText = "Момент начала конференции должен быть позднее, чем момент окончания"
            return false
        }

        if(globalDateStart > startDate){
            binding.startTimeTextField.helperText =
                "Момент начала конференции не может быть меньше начала родителя - " +
                        simpleDateFormatClientDate.format(globalDateStart.time) + " " +
                        simpleDateFormatClientTime.format(globalDateStart.time)
            return false
        }

        if(globalDateEnd < endDate){
            binding.endTimeTextField.helperText =
                "Момент окончания конференции не может быть позднее окончания родителя - " +
                        simpleDateFormatClientDate.format(globalDateEnd.time) + " " +
                        simpleDateFormatClientTime.format(globalDateEnd.time)
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
}