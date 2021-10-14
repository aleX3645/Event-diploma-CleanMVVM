package com.alex3645.feature_conference_builder.presentation.eventEditorView

import android.content.ClipDescription
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Event
import com.alex3645.feature_conference_builder.domain.model.Tariff
import com.alex3645.feature_conference_builder.presentation.conferenceEditorView.ConferenceEditorFragmentDirections
import com.alex3645.feature_conference_builder.presentation.conferenceEditorView.ConferenceEditorViewModel
import java.text.SimpleDateFormat
import java.util.*

class EventEditorViewModel : BaseViewModel<EventEditorViewModel.ViewState, EventEditorViewModel.Action>(ViewState()){

    var conference: Conference? = null
    var event: Event? = null

    var newEvent = Event(0,null,null,null,"", mutableListOf(),null,1)

    val simpleDateFormatServer = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'",Locale.getDefault())

    fun buildNewEvent(name: String, dateStart: Calendar, dateEnd: Calendar, description: String){
        newEvent.dateStart = simpleDateFormatServer.format(dateStart.time)
        newEvent.dateEnd = simpleDateFormatServer.format(dateEnd.time)

        newEvent.name = name
        newEvent.description = description
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = ""
    ) : BaseViewState

    interface Action : BaseAction {
        object BuildSuccess : Action
        class BuildFailure(val message: String) : Action
    }

    override fun onReduceState(viewAction: Action): ViewState {
        TODO("Not yet implemented")
    }

    fun navigateToEventListEditor(navController: NavController){
        val action = EventEditorFragmentDirections.actionEventBuilderToEventBuilderListFragment(null,newEvent)
        navController.navigate(action)
    }

    fun navigateBack(navController: NavController){
        conference?.let {
            conference?.events?.add(newEvent)
            navController.previousBackStackEntry?.savedStateHandle?.set("conference", conference)
            navController.popBackStack()
        }

        event?.let{
            conference?.events?.add(newEvent)
            navController.previousBackStackEntry?.savedStateHandle?.set("event", event)
            navController.popBackStack()
        }
    }
}