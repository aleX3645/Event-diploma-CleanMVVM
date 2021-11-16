package com.alex3645.feature_conference_detail.presentation.settingsView

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController


class SettingsViewModel: ViewModel(){

    var conferenceId: Long = 0

    fun navigateToStats(navController: NavController){
        val action = SettingsConferenceFragmentDirections.actionSettingsToStats(conferenceId.toInt())
        navController.navigate(action)
    }

    fun navigateBack(navController: NavController){
        navController.popBackStack()
    }
}