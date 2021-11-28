package com.alex3645.feature_conference_list.presentation.conferenceRecyclerView

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_list.di.component.DaggerConferenceViewModelComponent
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.usecase.LoadNextConferencesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConferenceRecyclerViewModel: BaseViewModel<ConferenceRecyclerViewModel.ViewState, ConferenceRecyclerViewModel.Action>(ViewState()){

    init{
        DaggerConferenceViewModelComponent.factory().create().inject(this)
    }

    var filterList: MutableList<Int> = mutableListOf()
    private val conferences: MutableList<Conference> = mutableListOf()

    var city: String = ""

    @Inject
    lateinit var loadNextConferencesUseCase: LoadNextConferencesUseCase

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val conferences: List<Conference> = listOf(),
        val errorMessage: String = "Произошла непредвиденная ошибка"
    ) : BaseViewState

    interface Action : BaseAction {
        class ConferenceListLoadingSuccess(val conferences: List<Conference>) : Action
        class ConferenceListLoadingFailure(val message: String) : Action
    }

    override fun onLoadData() {
        loadNextConferences()
    }

    fun loadDataFromStart() {
        conferences.clear()
        loadNextConferencesUseCase.dropData()

        onLoadData()
    }

    private fun loadNextConferences(){
        viewModelScope.launch {
            loadNextConferencesUseCase().also { result ->
                val action = when (result) {
                    is LoadNextConferencesUseCase.Result.Success ->
                        Action.ConferenceListLoadingSuccess(result.data)
                    is LoadNextConferencesUseCase.Result.Error ->
                        Action.ConferenceListLoadingFailure(result.e.message ?:"Произошла непредвиденная ошибка")
                    else -> Action.ConferenceListLoadingFailure("Произошла непредвиденная ошибка")
                }
                sendAction(action)
            }
        }
    }

    fun isUserOrganizer(context: Context) : Boolean{
        val spManager = SharedPreferencesManager(context)
        return spManager.fetchOrgFlag()
    }

    override fun onReduceState(viewAction: Action) = when (viewAction){
        is Action.ConferenceListLoadingSuccess -> {
            this.conferences.addAll(viewAction.conferences)
            state.copy(
                isLoading = false,
                isError = false,
                conferences = this.conferences
            )
        }
        is Action.ConferenceListLoadingFailure -> state.copy(
            isLoading = false,
            isError = true,
            errorMessage = viewAction.message
        )
        else -> state.copy(
            isLoading = false,
            isError = true,
            errorMessage = "Произошла непредвиденная ошибка"
        )
    }

    fun navigateToConferenceDetail(navController: NavController, conference: Conference){

        val action = ConferenceRecyclerFragmentDirections.actionConferenceListToConferenceDetail(conference.id)
        navController.navigate(action)
    }

    fun navigateToSearch(navController: NavController){
        val action = ConferenceRecyclerFragmentDirections.actionRecyclerToSearch()
        navController.navigate(action)
    }

    fun navigateToFilter(navController: NavController){
        val action = ConferenceRecyclerFragmentDirections.actionRecyclerToFilter(filterList.toIntArray(),city)
        navController.navigate(action)
    }

    fun navigateToConferenceBuilder(navController: NavController){
        val action = ConferenceRecyclerFragmentDirections.actionRecyclerToConferenceBuilderFeature()
        navController.navigate(action)
    }
}