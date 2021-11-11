package com.alex3645.feature_personal_schedule.presentation.personalScheduleView

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.alex3645.base.android.SharedPreferencesManager
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_personal_schedule.di.component.DaggerPersonalScheduleViewModelComponent
import com.alex3645.feature_personal_schedule.domain.model.Event
import com.alex3645.feature_personal_schedule.usecase.LoadAccountByLoginUseCase
import com.alex3645.feature_personal_schedule.usecase.LoadPersonalEventsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class PersonalScheduleViewModel(application: Application) : BaseAndroidViewModel<PersonalScheduleViewModel.ViewState, PersonalScheduleViewModel.Action>(ViewState(), application) {

    init{
        DaggerPersonalScheduleViewModelComponent.factory().create().inject(this)
    }

    var conferenceId = 0

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "Авторизация успешна",
        val events: List<Event>? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class LoadSuccess(val events: List<Event>) : Action
        class Failure(val message: String) : Action
    }

    @Inject
    lateinit var loadUserByIdUseCase: LoadAccountByLoginUseCase
    @Inject
    lateinit var loadPersonalEventsUseCase: LoadPersonalEventsUseCase


    fun loadPersonalEventsForUser(){
        viewModelScope.launch {
            val spManager = SharedPreferencesManager(getApplication())
            loadUserByIdUseCase(spManager.fetchLogin()?: "").also { result ->
                val action = when (result) {
                    is LoadAccountByLoginUseCase.Result.Success ->{
                        loadEvents(result.user.id.toLong(), spManager.fetchAuthToken()?:"")
                        return@also
                    }
                    is LoadAccountByLoginUseCase.Result.Error ->
                        Action.Failure(result.e.message ?: "Ошибка подключения")
                    else -> Action.Failure("Ошибка подключения")
                }
                sendAction(action)
            }
        }
    }

    private fun loadEvents(userId: Long, token: String){
        viewModelScope.launch {
            loadPersonalEventsUseCase(token,userId).also { result ->
                val action = when (result) {
                    is LoadPersonalEventsUseCase.Result.Success ->{
                        Log.d("!!!", "onLoadEventsSuccess")
                        Action.LoadSuccess(result.events)
                    }
                    is LoadPersonalEventsUseCase.Result.Error ->
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
                events = viewAction.events
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