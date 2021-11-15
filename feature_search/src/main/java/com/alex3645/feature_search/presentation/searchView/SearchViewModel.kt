package com.alex3645.feature_search.presentation.searchView

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_search.di.component.DaggerSearchViewModelComponent
import com.alex3645.feature_search.domain.data.Conference
import com.alex3645.feature_search.domain.data.Event
import com.alex3645.feature_search.domain.data.User
import com.alex3645.feature_search.usecase.SearchConferencesUseCase
import com.alex3645.feature_search.usecase.SearchEventsUseCase
import com.alex3645.feature_search.usecase.SearchUsersUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel : BaseViewModel<SearchViewModel.ViewState, SearchViewModel.Action>(ViewState()){

    init{
        DaggerSearchViewModelComponent.factory().create().inject(this)
    }

    @Inject
    lateinit var searchConferenceUseCase: SearchConferencesUseCase
    @Inject
    lateinit var searchEventsUseCase: SearchEventsUseCase
    @Inject
    lateinit var searchUsersUseCase: SearchUsersUseCase

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val conferences: List<Conference> = listOf(),
        val events: List<Event> = listOf(),
        val users: List<User> = listOf()
    ) : BaseViewState

    interface Action : BaseAction {
        class ConferenceListLoadingSuccess(val conferences: List<Conference>) : Action
        class EventListLoadingSuccess(val events: List<Event>) : Action
        class UserListLoadingSuccess(val users: List<User>) : Action
        object LoadingFailure : Action
    }

    fun searchConference(text: String){
        viewModelScope.launch {
            searchConferenceUseCase(text).also { result ->
                val action = when (result) {
                    is SearchConferencesUseCase.Result.Success ->
                        Action.ConferenceListLoadingSuccess(result.confList)
                    is SearchConferencesUseCase.Result.Error ->
                        Action.LoadingFailure
                    else -> Action.LoadingFailure
                }
                sendAction(action)
            }
        }
    }

    fun searchEvents(text: String){
        viewModelScope.launch {
            searchEventsUseCase(text).also { result ->
                val action = when (result) {
                    is SearchEventsUseCase.Result.Success ->
                        Action.EventListLoadingSuccess(result.eventList)
                    is SearchEventsUseCase.Result.Error ->
                        Action.LoadingFailure
                    else -> Action.LoadingFailure
                }
                sendAction(action)
            }
        }
    }

    fun searchUsers(text: String){
        viewModelScope.launch {
            searchUsersUseCase(text).also { result ->
                val action = when (result) {
                    is SearchUsersUseCase.Result.Success ->
                        Action.UserListLoadingSuccess(result.userList)
                    is SearchUsersUseCase.Result.Error ->
                        Action.LoadingFailure
                    else -> Action.LoadingFailure
                }
                sendAction(action)
            }
        }
    }

    private var conferences: MutableList<Conference> = mutableListOf()
    private var events: MutableList<Event> = mutableListOf()
    private var users: MutableList<User> = mutableListOf()
    override fun onReduceState(viewAction: Action) = when (viewAction){
        is Action.ConferenceListLoadingSuccess -> {
            this.conferences = viewAction.conferences as MutableList<Conference>
            state.copy(
                isLoading = false,
                isError = false,
                conferences = this.conferences
            )
        }
        is Action.EventListLoadingSuccess -> {
            this.events = viewAction.events as MutableList<Event>
            state.copy(
                isLoading = false,
                isError = false,
                events = this.events
            )
        }
        is Action.LoadingFailure -> state.copy(
            isLoading = false,
            isError = true
        )
        else -> state.copy(
            isLoading = false,
            isError = true
        )
    }

    fun navigateToConferenceDetail(navController: NavController, conferenceId: Int){
        val action = SearchFragmentDirections.actionSearchToConferenceDetail(conferenceId)
        navController.navigate(action)
    }

    fun navigateToUserAccount(navController: NavController, userId: Int){
        val action = SearchFragmentDirections.actionSearchToUserAccount(userId)
        navController.navigate(action)
    }

    fun navigateBack(navController: NavController){
        navController.popBackStack()
    }
}