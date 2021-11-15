package com.alex3645.feature_info_holder.presentation.infoManagerView

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class InfoManagerViewModel : ViewModel(){

    fun navigateToNoInternet(navController: NavController){
        val action = InfoManagerFragmentDirections.actionManagerToNoInternet()
        navController.navigate(action)
    }
}