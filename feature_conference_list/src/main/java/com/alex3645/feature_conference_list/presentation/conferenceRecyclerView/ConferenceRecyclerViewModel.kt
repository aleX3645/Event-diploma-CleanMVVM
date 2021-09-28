package com.alex3645.feature_conference_list.presentation.conferenceRecyclerView

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_list.di.component.DaggerFragmentComponent
import com.alex3645.feature_conference_list.di.component.DaggerViewModelComponent
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.usecase.LoadNextConferencesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConferenceRecyclerViewModel():
    BaseViewModel<ConferenceRecyclerViewModel.ViewState, ConferenceRecyclerViewModel.Action>(ViewState()){

    @Inject
    lateinit var loadNextConferencesUseCase: LoadNextConferencesUseCase

    init{
        DaggerViewModelComponent.factory().create().inject(this)
    }

    override fun onLoadData() {
        loadNextConferences()
    }

    fun loadDataFromStart() {
        conferences.clear()
        loadNextConferencesUseCase.dropData()

        onLoadData()
    }

    private val conferences: MutableList<Conference> = mutableListOf()
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
            isError = false,
            conferences = this.conferences
        )
        else -> state.copy(
            isLoading = false,
            isError = true,
            conferences = this.conferences
        )
    }

    private fun loadNextConferences(){
        viewModelScope.launch {
            loadNextConferencesUseCase().also { result ->
                val action = when (result) {
                    is LoadNextConferencesUseCase.Result.Success ->
                        if (result.data.isEmpty()) {
                            Action.ConferenceListLoadingFailure
                        } else {
                            Action.ConferenceListLoadingSuccess(result.data)
                        }
                    is LoadNextConferencesUseCase.Result.Error ->
                        Action.ConferenceListLoadingFailure
                    else -> Action.ConferenceListLoadingFailure
                }
                sendAction(action)
            }
        }
    }

    fun navigateToConferenceDetail(navController: NavController, conference: Conference){
        val action = ConferenceRecyclerFragmentDirections.actionConferenceListToConferenceDetail(conference)
        navController.navigate(action)
    }

    fun navigateToAccountAuth(navController: NavController){
        val action = ConferenceRecyclerFragmentDirections.actionRecyclerToAuth()
        navController.navigate(action)
    }

    fun navigateToSearch(navController: NavController){
        val action = ConferenceRecyclerFragmentDirections.actionRecyclerToSearch()
        navController.navigate(action)
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val conferences: List<Conference> = listOf()
    ) : BaseViewState

    interface Action : BaseAction {
        class ConferenceListLoadingSuccess(val conferences: List<Conference>) : Action
        object ConferenceListLoadingFailure : Action
    }
}