package com.alex3645.feature_conference_detail.presentation.conferenceStatsView

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.alex3645.base.android.SharedPreferencesManager
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailViewModelComponent
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.domain.model.Tariff
import com.alex3645.feature_conference_detail.domain.model.Ticket
import com.alex3645.feature_conference_detail.presentation.conferenceDetailView.ConferenceDetailViewModel
import com.alex3645.feature_conference_detail.usecase.LoadAccountByLoginUseCase
import com.alex3645.feature_conference_detail.usecase.LoadConferenceByIdUseCase
import com.alex3645.feature_conference_detail.usecase.RegisterTicketUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ConferenceStatsViewModel: BaseViewModel<ConferenceStatsViewModel.ViewState, ConferenceStatsViewModel.Action>(ViewState()) {

    init{
        DaggerConferenceDetailViewModelComponent.factory().create().inject(this)
    }

    var conferenceId = 0

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "Авторизация успешна",
        val conference: Conference? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class LoadSuccess(val conference: Conference) : Action
        class Failure(val message: String) : Action
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
                        Action.Failure("Ошибка подключения")
                    else -> Action.Failure("Ошибка подключения")
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
        is Action.Failure -> {
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