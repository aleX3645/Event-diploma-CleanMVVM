package com.alex3645.feature_conference_detail.presentation.eventRecyclerView

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alex3645.feature_conference_detail.domain.model.Event

class EventRecyclerViewModel : ViewModel() {
    fun navigateToEvent(navController: NavController, event: Event){
        val action = EventRecyclerFragmentDirections.actionEventListToEvent(0)
        navController.navigate(action)
    }
}