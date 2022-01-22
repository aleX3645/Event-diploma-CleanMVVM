package com.alex3645.feature_conference_builder.presentation.tariffEditorView

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Tariff

class TariffEditorViewModel: ViewModel() {
    var conference: Conference? = null
    var tariff: Tariff? = null

    fun saveTariff(){

        if(conference?.tariffs?.any { it -> it.id == tariff?.id } == true){
            conference?.let { c->
                tariff?.let {
                    c.tariffs = c.tariffs?.filter { it1 -> it1.id != it.id }?.toMutableList()
                    c.tariffs!!.add(it)
                }
            }
        }else{
            tariff?.id = conference?.tariffs?.let { getMaxId(it) }?.plus(1)!!
            conference?.let { c->
                tariff?.let { c.tariffs?.add(it) }
            }
        }
    }

    private fun getMaxId(tariffs:List<Tariff>): Int{
        if (tariffs.isEmpty()){
            return 0
        }
        return tariffs.maxOf { it.id }
    }

    fun navigateBack(navController: NavController){
        conference?.let { c->
            navController.previousBackStackEntry?.savedStateHandle?.set("conference", c)
            navController.popBackStack()
        }
    }
}