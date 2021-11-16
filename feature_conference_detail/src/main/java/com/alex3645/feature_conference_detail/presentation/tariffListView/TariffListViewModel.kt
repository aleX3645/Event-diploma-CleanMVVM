package com.alex3645.feature_conference_detail.presentation.tariffListView

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailViewModelComponent
import com.alex3645.feature_conference_detail.domain.model.Tariff
import com.alex3645.feature_conference_detail.domain.model.Ticket
import com.alex3645.feature_conference_detail.usecase.LoadAccountByLoginUseCase
import com.alex3645.feature_conference_detail.usecase.LoadConferenceByIdUseCase
import com.alex3645.feature_conference_detail.usecase.RegisterTicketUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TariffListViewModel(application: Application): BaseAndroidViewModel<TariffListViewModel.ViewState, TariffListViewModel.Action>(ViewState(),application) {

    init{
        DaggerConferenceDetailViewModelComponent.factory().create().inject(this)
    }

    var conferenceId = 0

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "Авторизация успешна",
        val tariffs: List<Tariff>? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class Success(val tariffs: List<Tariff>) : Action
        class Failure(val message: String) : Action
        object RegComplete: Action
    }

    @Inject
    lateinit var loadConferenceByIdUseCase: LoadConferenceByIdUseCase
    @Inject
    lateinit var loadAccountByLoginUseCase: LoadAccountByLoginUseCase
    @Inject
    lateinit var registerTicketUseCase: RegisterTicketUseCase

    fun loadTariffs(){
        viewModelScope.launch {
            loadConferenceByIdUseCase(conferenceId).also { result ->
                val action = when (result) {
                    is LoadConferenceByIdUseCase.Result.Success ->
                        Action.Success(result.data.tariffs)
                    is LoadConferenceByIdUseCase.Result.Error ->
                        Action.Failure("Ошибка подключения")
                    else -> Action.Failure("Ошибка подключения")
                }
                sendAction(action)
            }
        }
    }

    fun registerTicket(id: Long){
        viewModelScope.launch {
            val spManager = SharedPreferencesManager(getApplication())
            loadAccountByLoginUseCase(spManager.fetchLogin() ?: "").also { result ->
                val action = when (result) {
                    is LoadAccountByLoginUseCase.Result.Success ->{
                        registerTicket(userId = result.user.id.toLong(), tariffId = id, spManager.fetchAuthToken()?:"")
                        return@also
                    }
                    is LoadAccountByLoginUseCase.Result.Error ->
                        Action.Failure(result.e.message?:"Произошла непредвиденная ошибка")
                    else -> Action.Failure("Произошла непредвиденная ошибка")
                }
                sendAction(action)
            }
        }
    }

    private val simpleDateFormatServer = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
    private suspend fun registerTicket(userId: Long, tariffId: Long, token: String){
        Log.d("!!!", "regTicket")
        val ticket = Ticket(
            buyerId = userId,
            id = 0,
            released = simpleDateFormatServer.format(Calendar.getInstance(Locale.getDefault()).time),
            tariffId = tariffId
        )
        registerTicketUseCase(token = token, id = userId, ticket = ticket).also { result ->
            val action = when (result) {
                is RegisterTicketUseCase.Result.Success ->
                    Action.RegComplete
                is RegisterTicketUseCase.Result.Error ->
                    Action.Failure(result.e.message ?:"Произошла непредвиденная ошибка")
                else -> Action.Failure("Произошла непредвиденная ошибка")
            }
            sendAction(action)
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.Success -> {
            state.copy(
                isLoading = false,
                isError = false,
                tariffs = viewAction.tariffs
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

    fun navigateBack(navController: NavController){
        navController.popBackStack()
    }
}