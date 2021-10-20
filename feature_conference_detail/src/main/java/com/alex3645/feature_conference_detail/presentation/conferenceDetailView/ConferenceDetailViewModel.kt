package com.alex3645.feature_conference_detail.presentation.conferenceDetailView

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailViewModelComponent
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.usecase.LoadConferenceByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConferenceDetailViewModel: BaseViewModel<ConferenceDetailViewModel.ViewState, ConferenceDetailViewModel.Action>(ViewState()) {

    init{
        DaggerConferenceDetailViewModelComponent.factory().create().inject(this)
    }

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

    fun loadConference(id: Int){
        viewModelScope.launch {
            loadConferenceByIdUseCase(id).also { result ->
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

    fun navigateToEventList(navController: NavController, conference: Conference){
        val action = ConferenceDetailFragmentDirections.actionConferenceToEventList(conference.id)
        navController.navigate(action)
    }
}