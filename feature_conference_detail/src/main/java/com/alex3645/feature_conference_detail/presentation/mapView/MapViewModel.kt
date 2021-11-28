package com.alex3645.feature_conference_detail.presentation.mapView

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class MapViewModel : ViewModel(){
    fun navigateBack(navController: NavController){
        navController.popBackStack()
    }
}