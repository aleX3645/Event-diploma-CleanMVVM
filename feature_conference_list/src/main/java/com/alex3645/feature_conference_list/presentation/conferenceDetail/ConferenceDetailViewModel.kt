package com.alex3645.feature_conference_list.presentation.conferenceDetail

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.ConferenceRecyclerFragmentDirections

class ConferenceDetailViewModel: ViewModel() {
    fun navigateToEventList(navController: NavController, conference: Conference){
        val action = ConferenceDetailFragmentDirections.actionConferenceToEventList(conference)
        navController.navigate(action)
    }


}