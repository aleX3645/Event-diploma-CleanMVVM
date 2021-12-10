package com.alex3645.feature_ticket_list.presentation.ticketListView

import android.app.Activity
import android.app.Application
import androidx.lifecycle.viewModelScope
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.app.presentation.NavHostActivity
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_ticket_list.di.component.DaggerTicketListViewModelComponent
import com.alex3645.feature_ticket_list.domain.model.TicketInfo
import com.alex3645.feature_ticket_list.usecase.LoadTicketsInfoUseCase
import com.alex3645.feature_ticket_list.usecase.LoadTicketsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TicketListViewModel (application: Application) : BaseAndroidViewModel<TicketListViewModel.ViewState, TicketListViewModel.Action>(
    ViewState(), application) {

    init{
        DaggerTicketListViewModelComponent.factory().create().inject(this)
    }


    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "",
        val tickets: List<TicketInfo> = listOf()
    ) : BaseViewState

    interface Action : BaseAction {
        class LoadSuccess(val tickets: List<TicketInfo>) : Action
        class Failure(val message: String) : Action
    }

    @Inject
    lateinit var loadTicketsInfoUseCase: LoadTicketsInfoUseCase

    fun loadTickets(){
        viewModelScope.launch {
            val spManager = SharedPreferencesManager(getApplication())
            loadTicketsInfoUseCase(spManager.fetchLogin()?: "").also { result ->
                val action = when (result) {
                    is LoadTicketsInfoUseCase.Result.Success ->{
                        Action.LoadSuccess(result.data)
                    }
                    is LoadTicketsInfoUseCase.Result.Error ->
                        Action.Failure("Ошибка подключения")
                    else -> Action.Failure("Ошибка подключения")
                }

                sendAction(action)
            }
        }
    }

    fun isUserAthed(): Boolean{
        val spManager = SharedPreferencesManager(getApplication())

        return spManager.fetchLogin() != null
    }

    fun navigateWithDeepLink(activity: Activity, conferenceId: Int){
        val spManager = SharedPreferencesManager(activity)
        spManager.setDeepLink("conference:${conferenceId}")

        (activity as NavHostActivity).navigateToItemBottomNavigation(0)
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.LoadSuccess -> {
            state.copy(
                isLoading = false,
                isError = false,
                tickets = viewAction.tickets
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