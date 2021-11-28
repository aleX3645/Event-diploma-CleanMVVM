package com.alex3645.feature_conference_builder.presentation.conferenceEditorView

import android.app.Application
import android.content.ContentValues
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_builder.R
import com.alex3645.feature_conference_builder.di.components.DaggerBuilderViewModelComponent
import com.alex3645.feature_conference_builder.di.module.BuilderViewModelModule
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Event
import com.alex3645.feature_conference_builder.domain.model.Tariff
import com.alex3645.feature_conference_builder.domain.model.User
import com.alex3645.feature_conference_builder.usecase.SaveConferenceUseCase
import com.alex3645.feature_conference_builder.usecase.SearchUsersUseCase
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class ConferenceEditorViewModel (application: Application):
    BaseAndroidViewModel<ConferenceEditorViewModel.ViewState, ConferenceEditorViewModel.Action>(ViewState(),application) {

    val token = AutocompleteSessionToken.newInstance()
    private val places: PlacesClient

    init{
        DaggerBuilderViewModelComponent.factory().create(BuilderViewModelModule(application)).inject(this)
        if (!Places.isInitialized()) {
            Places.initialize(application, application.resources.getString(R.string.google_maps_key), Locale.getDefault())
        }
        places = Places.createClient(application)
    }

    var conference: Conference = Conference(0,0,null,null,"",
        mutableListOf<Event>(),false,"",null,-1,0,mutableListOf<Tariff>(),null)

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = ""
    ) : BaseViewState

    interface Action : BaseAction {
        object BuildSuccess : Action
        class BuildFailure(val message: String) : Action
    }

    @Inject
    lateinit var saveConferenceUseCase: SaveConferenceUseCase

    fun saveConference(){
        viewModelScope.launch {
            val spManager = SharedPreferencesManager(getApplication())
            conference.organizerLogin = spManager.fetchLogin()

            saveConferenceUseCase(conference).also { result ->
                val action = when (result) {
                    is SaveConferenceUseCase.Result.Success ->
                        if (result.response.success) {
                            Action.BuildSuccess
                        } else {
                            Action.BuildFailure(result.response.message)
                        }
                    is SaveConferenceUseCase.Result.Error ->
                        Action.BuildFailure("Ошибка сервера")
                    else -> Action.BuildFailure("Ошибка подключения")
                }
                sendAction(action)
            }
        }
    }

    fun getPredictionAdapter(text: String, callback: (arrayAdapter: ArrayAdapter<String>)->Unit){
        val request =
            FindAutocompletePredictionsRequest.builder()
                .setTypeFilter(TypeFilter.CITIES)
                .setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(token)
                .setQuery(text)
                .build()

        places.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                callback.invoke(ArrayAdapter(this.getApplication(), R.layout.dropdown_menu_item_builder, response.autocompletePredictions.map { it.getFullText(null).toString()}))
            }.addOnFailureListener { exception: Exception? ->
                if (exception is ApiException) {
                    Log.e(ContentValues.TAG, "Place not found: " + exception.message)
                }
            }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.BuildSuccess -> {
            state.copy(
                isLoading = false,
                isError = false
            )
        }
        is Action.BuildFailure -> {
            val message = viewAction.message
            state.copy(
                isLoading = false,
                isError = true,
                errorMessage = message
            )
        }
        else -> {
            state.copy(
                isLoading = false,
                isError = true,
                errorMessage = "Произошла непредвиденная ошибка"
            )
        }
    }

    fun navigateToEventListEditor(navController: NavController){
        val action = ConferenceEditorFragmentDirections.actionConferenceBuilderToEventEditorFragment(conference,null)
        navController.navigate(action)
    }

    fun navigateToTariffList(navController: NavController){
        val action = ConferenceEditorFragmentDirections.actionConferenceBuilderToTariffListFragment(conference)
        navController.navigate(action)
    }

    fun navigateBack(navController: NavController){
        navController.popBackStack()
    }
}