package com.alex3645.feature_conference_detail.presentation.eventRecyclerView

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailViewModelComponent
import com.alex3645.feature_conference_detail.domain.model.Event
import com.alex3645.feature_conference_detail.presentation.conferenceDetailHolderView.ConferenceDetailHolderFragmentDirections
import com.alex3645.feature_conference_detail.usecase.LoadEventByIdUseCase
import com.alex3645.feature_conference_detail.usecase.LoadEventsForConferenceWithIdUseCase
import com.alex3645.feature_conference_detail.usecase.LoadEventsForEventWithIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventRecyclerViewModel: BaseViewModel<EventRecyclerViewModel.ViewState, EventRecyclerViewModel.Action>(ViewState()) {
    init{
        DaggerConferenceDetailViewModelComponent.factory().create().inject(this)
    }

    var conferenceId = 0
    var eventId = 0

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "Авторизация успешна",
        val events: List<Event>? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class LoadSuccess(val events: List<Event>) : Action
        class AuthFailure(val message: String) : Action
    }

    @Inject
    lateinit var loadEventsForConferenceWithIdUseCase: LoadEventsForConferenceWithIdUseCase
    @Inject
    lateinit var loadEventsForEventWithIdUseCase: LoadEventsForEventWithIdUseCase

    fun loadEventsForConference(){
        viewModelScope.launch {
            Log.d("!!!", "eventId - " + eventId)
            if(eventId != 0){
                loadEventsForEventWithIdUseCase(eventId).also { result ->
                    val action = when (result) {
                        is LoadEventsForEventWithIdUseCase.Result.Success ->
                            Action.LoadSuccess(result.data)
                        is LoadEventsForEventWithIdUseCase.Result.Error ->
                            Action.AuthFailure("Ошибка подключения")
                        else -> Action.AuthFailure("Ошибка подключения")
                    }
                    sendAction(action)
                }
            }else{
                loadEventsForConferenceWithIdUseCase(conferenceId).also { result ->
                    val action = when (result) {
                        is LoadEventsForConferenceWithIdUseCase.Result.Success ->
                            Action.LoadSuccess(result.data)
                        is LoadEventsForConferenceWithIdUseCase.Result.Error ->
                            Action.AuthFailure("Ошибка подключения")
                        else -> Action.AuthFailure("Ошибка подключения")
                    }
                    sendAction(action)
                }
            }
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.LoadSuccess -> {
            state.copy(
                isLoading = false,
                isError = false,
                events = viewAction.events
            )
        }
        is Action.AuthFailure -> {
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

    fun navigateToEventByNavigation(navController: NavController, id: Int){
        val action = EventRecyclerFragmentDirections.actionEventListToEvent(id)
        navController.navigate(action)
    }

    fun navigateToEventByParent(navController: NavController, id: Int){
        val action = ConferenceDetailHolderFragmentDirections.actionHolderToEvent(id)
        navController.navigate(action)
    }

    fun navigateBack(navController: NavController){
        navController.popBackStack()
    }
}