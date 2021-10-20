package com.alex3645.feature_conference_detail.presentation.eventRecyclerView

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailViewModelComponent
import com.alex3645.feature_conference_detail.domain.model.Event
import com.alex3645.feature_conference_detail.usecase.LoadEventsForConferenceWithIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventRecyclerViewModel: BaseViewModel<EventRecyclerViewModel.ViewState, EventRecyclerViewModel.Action>(ViewState()) {
    init{
        DaggerConferenceDetailViewModelComponent.factory().create().inject(this)
    }

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

    fun loadEventsForConference(id: Int){
        viewModelScope.launch {
            loadEventsForConferenceWithIdUseCase(id).also { result ->
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

    fun navigateToEvent(navController: NavController, id: Int){
        val action = EventRecyclerFragmentDirections.actionEventListToEvent(id)
        navController.navigate(action)
    }
}