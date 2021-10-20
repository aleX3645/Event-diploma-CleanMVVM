package com.alex3645.feature_conference_list.presentation.filterView

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class FilterViewModel : ViewModel() {

    var filterList: MutableList<Int> = mutableListOf()

    fun navigateBack(navController: NavController){
        navController.previousBackStackEntry?.savedStateHandle?.set("filter", filterList.toIntArray())
        navController.popBackStack()
    }
}