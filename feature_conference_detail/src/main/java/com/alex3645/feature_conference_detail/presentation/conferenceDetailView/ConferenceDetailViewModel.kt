package com.alex3645.feature_conference_detail.presentation.conferenceDetailView

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailViewModelComponent
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.presentation.conferenceDetailHolderView.ConferenceDetailHolderFragmentArgs
import com.alex3645.feature_conference_detail.presentation.conferenceDetailHolderView.ConferenceDetailHolderFragmentDirections
import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.EventRecyclerFragmentDirections
import com.alex3645.feature_conference_detail.usecase.LoadConferenceByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConferenceDetailViewModel: BaseViewModel<ConferenceDetailViewModel.ViewState, ConferenceDetailViewModel.Action>(ViewState()) {

    init{
        DaggerConferenceDetailViewModelComponent.factory().create().inject(this)
    }

    var conferenceId: Int = 0

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "Авторизация успешна",
        val conference: Conference? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class LoadSuccess(val conference: Conference) : Action
        class AuthFailure(val message: String) : Action
    }

    @Inject
    lateinit var loadConferenceByIdUseCase: LoadConferenceByIdUseCase

    fun loadConference(){
        viewModelScope.launch {
            loadConferenceByIdUseCase(conferenceId).also { result ->
                val action = when (result) {
                    is LoadConferenceByIdUseCase.Result.Success ->
                        Action.LoadSuccess(result.data)
                    is LoadConferenceByIdUseCase.Result.Error ->
                        Action.AuthFailure("Ошибка подключения")
                    else -> Action.AuthFailure("Ошибка подключения")
                }
                sendAction(action)
            }
        }
    }

    fun navigateToTariffs(navController: NavController){
        val action = ConferenceDetailHolderFragmentDirections.actionDetailHolderToTariffList(conferenceId)
        navController.navigate(action)
    }

    fun navigateToEventList(navController: NavController){
        val action = ConferenceDetailHolderFragmentDirections.actionDetailHolderToTariffList(conferenceId)
        navController.navigate(action)
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.LoadSuccess -> {
            state.copy(
                isLoading = false,
                isError = false,
                conference = viewAction.conference
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