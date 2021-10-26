package com.alex3645.feature_conference_builder.presentation.eventEditorView

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Event
import java.text.SimpleDateFormat
import java.util.*

class EventEditorViewModel : ViewModel(){

    var conference: Conference? = null
    var event: Event? = null

    var newEvent: Event? = null

    private val simpleDateFormatServer = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",Locale.getDefault())

    fun buildNewEvent(name: String, dateStart: Calendar, dateEnd: Calendar, description: String){
        newEvent = Event(
            0,
            0,
            simpleDateFormatServer.format(dateEnd.time),
            simpleDateFormatServer.format(dateStart.time),
            description,
            mutableListOf(),
            name,
            0)
    }

    fun navigateToEventListEditor(navController: NavController){
        val action = EventEditorFragmentDirections.actionEventBuilderToEventBuilderListFragment(null,newEvent)
        navController.navigate(action)
    }

    fun navigateBack(navController: NavController){
        newEvent?.let { itNewEvent ->
            conference?.let {
                itNewEvent.conferenceId = it.id
                it.events!!.add(itNewEvent)
                navController.previousBackStackEntry?.savedStateHandle?.set("conference", conference)
                navController.popBackStack()
            }

            event?.let{
                it.events!!.add(itNewEvent)
                navController.previousBackStackEntry?.savedStateHandle?.set("event", event)
                navController.popBackStack()
            }
        }
    }
}