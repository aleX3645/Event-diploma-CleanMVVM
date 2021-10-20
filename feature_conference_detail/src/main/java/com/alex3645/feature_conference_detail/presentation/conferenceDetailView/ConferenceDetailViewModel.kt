package com.alex3645.feature_conference_detail.presentation.conferenceDetailView

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alex3645.feature_conference_detail.domain.model.Conference

class ConferenceDetailViewModel: ViewModel() {
    fun navigateToEventList(navController: NavController, conference: Conference){
        val action = ConferenceDetailFragmentDirections.actionConferenceToEventList(conference.id)
        navController.navigate(action)
    }
}