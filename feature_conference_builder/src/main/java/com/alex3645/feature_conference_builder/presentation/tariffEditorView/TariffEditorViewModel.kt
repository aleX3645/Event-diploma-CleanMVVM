package com.alex3645.feature_conference_builder.presentation.tariffEditorView

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Tariff

class TariffEditorViewModel: ViewModel() {
    var conference: Conference? = null
    var tariff: Tariff? = null

    fun navigateBack(navController: NavController){
        conference?.let { c->
            tariff?.let {
                c.tariffs?.add(it)
            }
            navController.previousBackStackEntry?.savedStateHandle?.set("conference", c)
            navController.popBackStack()
        }
    }
}