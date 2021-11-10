package com.alex3645.feature_conference_detail.presentation.conferenceDetailHolderView

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class ConferenceDetailHolderViewModel: ViewModel() {
    var conferenceId: Long = 0

    fun navigateToSettings(navController: NavController){
        val action = ConferenceDetailHolderFragmentDirections.actionDetailHolderToSettings(conferenceId.toInt())
        navController.navigate(action)
    }
}