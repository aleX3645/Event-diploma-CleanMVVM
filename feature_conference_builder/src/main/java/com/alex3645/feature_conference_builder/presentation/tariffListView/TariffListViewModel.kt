package com.alex3645.feature_conference_builder.presentation.tariffListView

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Event
import com.alex3645.feature_conference_builder.domain.model.Tariff
import com.alex3645.feature_conference_builder.presentation.eventEditorListView.EventEditorListFragmentDirections

class TariffListViewModel: ViewModel() {
    var conference: Conference? = null

    fun navigateBack(navController: NavController){
        conference?.let {
            navController.previousBackStackEntry?.savedStateHandle?.set("conference", conference)
            navController.popBackStack()
        }
    }

    fun navigateToTariffEditor(navController: NavController){
        conference?.let{
            val action = TariffListFragmentDirections.actionTariffListToTariffEditorFragment(0,conference)
            navController.navigate(action)
        }
    }

    fun navigateToTariffEditorSpecified(navController: NavController, id: Int){
        conference?.let{
            val action = TariffListFragmentDirections.actionTariffListToTariffEditorFragment(id,conference)
            navController.navigate(action)
        }
    }
}