package com.alex3645.feature_conference_detail.presentation.conferenceDetail

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alex3645.feature_conference_list.domain.model.Conference

class ConferenceDetailViewModel: ViewModel() {
    fun navigateToEventList(navController: NavController, conference: Conference){
        val action = ConferenceDetailFragmentDirections.actionConferenceToEventList(conference)
        navController.navigate(action)
    }


}