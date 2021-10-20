package com.alex3645.feature_conference_detail.presentation.eventDetailView

import androidx.lifecycle.viewModelScope
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailViewModelComponent
import com.alex3645.feature_conference_detail.domain.model.Event
import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.EventRecyclerViewModel
import com.alex3645.feature_conference_detail.usecase.LoadEventByIdUseCase
import com.alex3645.feature_conference_detail.usecase.LoadEventsForConferenceWithIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventDetailViewModel: BaseViewModel<EventDetailViewModel.ViewState, EventDetailViewModel.Action>(ViewState()) {

    init{
        DaggerConferenceDetailViewModelComponent.factory().create().inject(this)
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "Авторизация успешна",
        val event: Event? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class LoadSuccess(val event: Event) : Action
        class AuthFailure(val message: String) : Action
    }

    @Inject
    lateinit var loadEventByIdUseCase: LoadEventByIdUseCase

    fun loadEventsForConference(id: Int){
        viewModelScope.launch {
            loadEventByIdUseCase(id).also { result ->
                val action = when (result) {
                    is LoadEventByIdUseCase.Result.Success ->
                        Action.LoadSuccess(result.data)
                    is LoadEventByIdUseCase.Result.Error ->
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
                event = viewAction.event
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

}