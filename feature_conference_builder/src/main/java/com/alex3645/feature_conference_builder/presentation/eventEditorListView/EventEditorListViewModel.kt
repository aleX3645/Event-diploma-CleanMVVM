package com.alex3645.feature_conference_builder.presentation.eventEditorListView

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Event

class EventEditorListViewModel : ViewModel(){

    var conference: Conference? = null
    var event: Event? = null

    fun navigateToEventEditor(navController: NavController){
        if(event != null){
            val action = EventEditorListFragmentDirections.actionEventBuilderListToEventBuilderFragment(event, null)
            navController.navigate(action)
        }

        if(conference != null){
            val action = EventEditorListFragmentDirections.actionEventBuilderListToEventBuilderFragment(null, conference)
            navController.navigate(action)
        }
    }

    fun navigateBack(navController: NavController){
        conference?.let {
            navController.previousBackStackEntry?.savedStateHandle?.set("conference", conference)
            navController.popBackStack()
        }

        event?.let{
            navController.previousBackStackEntry?.savedStateHandle?.set("event", event)
            navController.popBackStack()
        }
    }
}