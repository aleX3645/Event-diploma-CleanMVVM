package com.alex3645.feature_ticket_list.presentation.ticketListView

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_ticket_list.di.component.DaggerTicketListViewModelComponent
import com.alex3645.feature_ticket_list.domain.model.Ticket
import com.alex3645.feature_ticket_list.usecase.LoadTicketsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TicketListViewModel (application: Application) : BaseAndroidViewModel<TicketListViewModel.ViewState, TicketListViewModel.Action>(
    ViewState(), application) {

    init{
        DaggerTicketListViewModelComponent.factory().create().inject(this)
    }

    @Inject
    lateinit var loadTicketsUseCase: LoadTicketsUseCase


    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "Авторизация успешна",
        val tickets: List<Ticket>? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class LoadSuccess(val tickets: List<Ticket>) : Action
        class Failure(val message: String) : Action
    }

    fun loadTickets(){
        viewModelScope.launch {
            val spManager = SharedPreferencesManager(getApplication())
            loadTicketsUseCase(spManager.fetchLogin()?: "").also { result ->
                val action = when (result) {
                    is LoadTicketsUseCase.Result.Success ->{
                        Action.LoadSuccess(result.events)
                    }
                    is LoadTicketsUseCase.Result.Error ->
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