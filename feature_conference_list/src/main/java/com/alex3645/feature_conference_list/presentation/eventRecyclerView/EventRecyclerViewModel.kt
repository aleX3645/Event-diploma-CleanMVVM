package com.alex3645.feature_conference_list.presentation.eventRecyclerView

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.domain.model.Event
import com.alex3645.feature_conference_list.presentation.conferenceDetail.ConferenceDetailFragmentDirections

class EventRecyclerViewModel : ViewModel() {
    fun navigateToEvent(navController: NavController, event: Event){
        val action = EventRecyclerFragmentDirections.actionEventListToEvent(event)
        navController.navigate(action)
    }
}