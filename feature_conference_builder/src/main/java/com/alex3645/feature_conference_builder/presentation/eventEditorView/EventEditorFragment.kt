package com.alex3645.feature_conference_builder.presentation.eventEditorView

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_builder.R
import com.alex3645.feature_conference_builder.databinding.FragmentEventEditorBinding
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Event
import com.alex3645.feature_conference_builder.domain.model.User
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
/*
        val observer = Observer<List<User>> {

            context?.let{ it1 ->
                (binding.loginTextField.editText as AutoCompleteTextView).setAdapter(ArrayAdapter(
                    it1, R.layout.dropdown_menu_item_builder, it))
            }

            (binding.loginTextField.editText as AutoCompleteTextView).showDropDown()
        }

        viewModel.usersPrediction.observe(viewLifecycleOwner,observer)*/

        observe(viewModel.stateLiveData, stateObserver)
        Log.d("!!!","test-1")
        args.conference?.let {
            viewModel.conference = it
            globalDateStart.time = simpleDateFormatServer.parse(it!!.dateStart)
            globalDateEnd.time = simpleDateFormatServer.parse(it!!.dateEnd)
            Log.d("!!!","test-2")
            if(args.editableEventId >= 0){
                Log.d("!!!","test-3")
                val ev = it.events!!.first { e -> e.id == args.editableEventId }
                initFields(ev)
            }
        }

        args.event?.let{
            viewModel.event = it
            globalDateStart.time = simpleDateFormatServer.parse(it!!.dateStart)
            globalDateEnd.time = simpleDateFormatServer.parse(it!!.dateEnd)

            if(args.editableEventId >= 0){
                Log.d("!!!","test-3")
                val ev = it.events!!.first { e -> e.id == args.editableEventId }
                initFields(ev)
            }
        }

        initBackStackObserver()
        initActions()
    }

    private fun initFields(event: Event){
        startDate.time = simpleDateFormatServer.parse(event.dateStart)
        endDate.time = simpleDateFormatServer.parse(event.dateEnd)

        binding.loginInputText.text = Editable.Factory.getInstance().newEditable(event.speakerLogin)
        binding.nameInputText.text = Editable.Factory.getInstance().newEditable(event.name)
        binding.descriptionInputText.text = Editable.Factory.getInstance().newEditable(event.description)

        binding.startDateInputText.text = Editable.Factory.getInstance().newEditable(
            simpleDateFormatClientDate.format(simpleDateFormatServer.parse(event.dateStart))
        )
        binding.endDateInputText.text = Editable.Factory.getInstance().newEditable(
            simpleDateFormatClientDate.format(simpleDateFormatServer.parse(event.dateEnd))
        )

        binding.startTimeInputText.text = Editable.Factory.getInstance().newEditable(
            simpleDateFormatClientTime.format(simpleDateFormatServer.parse(event.dateStart))
        )

        binding.endTimeInputText.text = Editable.Factory.getInstance().newEditable(
            simpleDateFormatClientTime.format(simpleDateFormatServer.parse(event.dateEnd))
        )
    }

    private fun initBackStackObserver(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Event>("event")
            ?.observe(viewLifecycleOwner) {
                viewModel.newEvent = it
            }
    }

    private fun initActions(){
        /*
        binding.loginInputText.addTextChangedListener{
            viewModel.searchUsers(it.toString())
        }*/

        setStartDateTimeActions()
        setEndDateTimeActions()

        setButtons()
    }

    var moveConsider = 0
    private fun setButtons(){
        binding.backButton.setOnClickListener {
            viewModel.navigateBack(findNavController())
        }

        binding.addEvents.setOnClickListener {
            if(save()){
                moveConsider= 0
                viewModel.loadUserByLogin(binding.loginInputText.text.toString())
            }
        }

        binding.saveButton.setOnClickListener {
            if(save()){
                moveConsider=1
                viewModel.loadUserByLogin(binding.loginInputText.text.toString())
            }
        }
    }

    private fun save() : Boolean{
        if(binding.endDateInputText.text.contentEquals("")){
            binding.endDateTextField.helperText = "?????? ?????????????????????? ?????????????? ???????????????? ?????????????? ????????"
            return false
        }
        binding.endDateTextField.helperText = ""

        if(binding.startDateInputText.text.contentEquals("")){
            binding.startDateTextField.helperText = "?????? ?????????????????????? ?????????????? ???????????????? ?????????????? ????????"
            return false
        }
        binding.startDateTextField.helperText = ""

        if(binding.startTimeInputText.text.contentEquals("")){
            binding.startTimeTextField.helperText = "?????? ?????????????????????? ?????????????? ???????????????? ?????????????? ????????"
            return false
        }
        binding.startTimeTextField.helperText = ""

        if(binding.endTimeInputText.text.contentEquals("")){
            binding.endTimeTextField.helperText = "?????? ?????????????????????? ?????????????? ???????????????? ?????????????? ????????"
            return false
        }
        binding.endTimeTextField.helperText = ""

        if(binding.nameInputText.text.contentEquals("")){
            binding.eventNameTextField.helperText = "?????? ?????????????????????? ?????????????? ???????????????? ?????????????? ????????"
            return false
        }
        binding.eventNameTextField.helperText = ""


        if(correctDateTimeCheck(startDate, endDate)){
            if(args.editableEventId>=0){
                viewModel.event?.let{ it ->
                    it.events = it.events!!.filterNot { it.id == args.editableEventId }.toMutableList()
                    viewModel.buildNewEvent(
                        binding.nameInputText.text.toString(),
                        startDate,
                        endDate,
                        binding.descriptionInputText.text.toString(),
                        binding.loginInputText.text.toString(),getMaxId(it.events!!)+1)
                }

                viewModel.conference?.let{ it ->
                    it.events = it.events!!.filterNot { it.id == args.editableEventId }.toMutableList()
                    viewModel.buildNewEvent(
                        binding.nameInputText.text.toString(),
                        startDate,
                        endDate,
                        binding.descriptionInputText.text.toString(),
                        binding.loginInputText.text.toString(),getMaxId(it.events!!)+1)
                }
            }else{

                viewModel.event?.let{ it ->
                    viewModel.buildNewEvent(
                        binding.nameInputText.text.toString(),
                        startDate,
                        endDate,
                        binding.descriptionInputText.text.toString(),
                        binding.loginInputText.text.toString(),getMaxId(it.events!!)+1)
                }

                viewModel.conference?.let{ it ->
                    viewModel.buildNewEvent(
                        binding.nameInputText.text.toString(),
                        startDate,
                        endDate,
                        binding.descriptionInputText.text.toString(),
                        binding.loginInputText.text.toString(),getMaxId(it.events!!)+1)
                }
            }

            return true
        }

        return false
    }

    private fun getMaxId(events:List<Event>): Int{
        if (events.isEmpty()){
            return 0
        }
        return events.maxOf { it.id }
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
                startDate.set(Calendar.HOUR_OF_DAY, 0)
                startDate.set(Calendar.MINUTE, 0)
                startDate.set(Calendar.SECOND, 0)
                startDate.set(Calendar.MILLISECOND,0)

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
                startDate.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                startDate.set(Calendar.MINUTE, timePicker.minute)
                startDate.set(Calendar.SECOND, 0)
                startDate.set(Calendar.MILLISECOND,0)

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
                endDate.set(Calendar.HOUR_OF_DAY, 0)
                endDate.set(Calendar.MINUTE, 0)
                endDate.set(Calendar.SECOND, 0)
                endDate.set(Calendar.MILLISECOND,0)

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
                endDate.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                endDate.set(Calendar.MINUTE, timePicker.minute)
                endDate.set(Calendar.SECOND, 0)
                endDate.set(Calendar.MILLISECOND,0)

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
            binding.endDateTextField.helperText = "???????? ???????????? ?????????????????????? ???? ?????????? ???????? ???????????? ???????? ??????????????????"
            return false
        }
        if(endDate<=startDate && dateSettledFlag() && timeSettledFlag()){
            binding.endTimeTextField.helperText = "???????????? ???????????? ?????????????????????? ???????????? ???????? ??????????????, ?????? ???????????? ??????????????????"
            return false
        }

        if(globalDateStart > startDate){
            Log.d("testDateTimeBug", globalDateEnd.toString() + " " + endDate.toString())
            binding.startTimeTextField.helperText =
                "???????????? ???????????? ?????????????????????? ???? ?????????? ???????? ???????????? ???????????? ???????????????? - " +
                        simpleDateFormatClientDate.format(globalDateStart.time) + " " +
                        simpleDateFormatClientTime.format(globalDateStart.time)
            return false
        }

        if(globalDateEnd < endDate){
            Log.d("testDateTimeBug", globalDateEnd.toString() + " " + endDate.toString())
            binding.endTimeTextField.helperText =
                "???????????? ?????????????????? ?????????????????????? ???? ?????????? ???????? ?????????????? ?????????????????? ???????????????? - " +
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

    private val stateObserver = Observer<EventEditorViewModel.ViewState> {
        if(it.isError){
            binding.loginTextField.helperText = "???????????????????????? ???? ????????????"
        }else{
            if (moveConsider == 0) {
                viewModel.navigateToEventListEditor(findNavController())
            } else {
                viewModel.navigateBack(findNavController())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}