package com.alex3645.feature_conference_detail.presentation.settingsView

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailViewModelComponent
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.domain.model.Event
import com.alex3645.feature_conference_detail.presentation.eventDetailView.EventDetailViewModel
import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.EventRecyclerViewModel
import com.alex3645.feature_conference_detail.usecase.LoadAccountByIdUseCase
import com.alex3645.feature_conference_detail.usecase.LoadAccountByLoginUseCase
import com.alex3645.feature_conference_detail.usecase.LoadConferenceByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject


class SettingsViewModel(application: Application): BaseAndroidViewModel<SettingsViewModel.ViewState, SettingsViewModel.Action>(ViewState(),application){

    init{
        DaggerConferenceDetailViewModelComponent.factory().create().inject(this)
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "",
        val userOrganizer: Boolean = false
    ) : BaseViewState

    interface Action : BaseAction {
        class LoadSuccess(val userOrgFlag: Boolean) : Action
        class LoadFailure(val message: String) : Action
    }

    var conferenceId: Long = 0

    @Inject
    lateinit var loadConferenceByIdUseCase: LoadConferenceByIdUseCase
    @Inject
    lateinit var loadAccountByLoginUseCase: LoadAccountByLoginUseCase

    fun determineMenuType(){
        val spManager = SharedPreferencesManager(this.getApplication())
        if(spManager.fetchOrgFlag()){
            viewModelScope.launch {
                loadConferenceByIdUseCase(conferenceId.toInt()).also { result ->
                    when (result) {
                        is LoadConferenceByIdUseCase.Result.Success ->{

                            if(isUserWithLoginOrganizerOfThisConference(spManager.fetchLogin()?:"",result.data)){
                                sendAction(Action.LoadSuccess(true))
                            }else{
                                sendAction(Action.LoadSuccess(false))
                            }
                        }
                        is LoadConferenceByIdUseCase.Result.Error ->
                            sendAction(Action.LoadFailure("Ошибка подключения"))
                        else -> sendAction(Action.LoadFailure("Ошибка подключения"))
                    }
                }
            }
        }else{
            sendAction(Action.LoadSuccess(false))
        }
    }

    private suspend fun isUserWithLoginOrganizerOfThisConference(login: String, conference: Conference): Boolean{
        loadAccountByLoginUseCase(login).also { result ->
            when (result) {
                is LoadAccountByLoginUseCase.Result.Success ->{
                    return conference.organizerId == result.user.id
                }
                is LoadAccountByLoginUseCase.Result.Error ->{
                    return false
                }
                else ->
                    return false
                }
            }
        }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.LoadSuccess -> {
            state.copy(
                isLoading = false,
                isError = false,
                userOrganizer = viewAction.userOrgFlag
            )
        }
        is Action.LoadFailure -> {
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
                errorMessage = ""
            )
        }
    }

    fun navigateToStats(navController: NavController){
        val action = SettingsConferenceFragmentDirections.actionSettingsToStats(conferenceId.toInt())
        navController.navigate(action)
    }

    fun navigateBack(navController: NavController){
        navController.popBackStack()
    }
}