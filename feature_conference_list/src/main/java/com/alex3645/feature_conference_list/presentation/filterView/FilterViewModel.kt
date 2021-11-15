package com.alex3645.feature_conference_list.presentation.filterView

import android.app.Application
import android.content.ContentValues
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController
import com.alex3645.feature_event_list.R
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import java.util.*

class FilterViewModel(application: Application) : AndroidViewModel(application) {

    var filterList: MutableList<Int> = mutableListOf()
    var city: String = ""

    private val token = AutocompleteSessionToken.newInstance()
    private val places: PlacesClient

    init{
        if (!Places.isInitialized()) {
            Places.initialize(application, application.resources.getString(R.string.google_maps_key), Locale.getDefault())
        }
        places = Places.createClient(application)
    }

    fun getPredictionAdapter(text: String, callback: (arrayAdapter: ArrayAdapter<String>)->Unit){
        val request =
            FindAutocompletePredictionsRequest.builder()
                .setTypeFilter(TypeFilter.CITIES)
                .setSessionToken(token)
                .setQuery(text)
                .build()

        places.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                callback.invoke(ArrayAdapter(this.getApplication(), R.layout.dropdown_menu_item, response.autocompletePredictions.map { it.getPrimaryText(null).toString()}))
            }.addOnFailureListener { exception: Exception? ->
                if (exception is ApiException) {
                    Log.e(ContentValues.TAG, "Place not found: " + exception.message)
                }
            }
    }

    fun navigateBack(navController: NavController){
        navController.previousBackStackEntry?.savedStateHandle?.set("filter", filterList.toIntArray())
        navController.previousBackStackEntry?.savedStateHandle?.set("city", city)
        navController.popBackStack()
    }
}