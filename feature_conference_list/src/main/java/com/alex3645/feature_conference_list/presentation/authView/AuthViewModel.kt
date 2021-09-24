package com.alex3645.feature_conference_list.presentation.authView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex3645.base.delegate.observer
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_list.data.model.AuthResponse
import com.alex3645.feature_conference_list.di.component.DaggerViewModelComponent
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.ConferenceRecyclerViewModel
import com.alex3645.feature_conference_list.usecase.AuthUseCase
import com.alex3645.feature_conference_list.usecase.LoadNextConferencesUseCase
import kotlinx.coroutines.launch
import java.lang.Error
import java.net.PasswordAuthentication
import javax.inject.Inject

class AuthViewModel : BaseViewModel<AuthViewModel.ViewState, AuthViewModel.Action>(ViewState()) {

    init{
        DaggerViewModelComponent.factory().create().inject(this)
    }

    @Inject
    lateinit var authUseCase: AuthUseCase


    fun tryAuth(passwordAuthentication: PasswordAuthentication){
        viewModelScope.launch {
            authUseCase(passwordAuthentication).also { result ->
                val action = when (result) {
                    is AuthUseCase.Result.Success ->
                        if (result.authResponse.success) {
                            Action.AuthSuccess
                        } else {
                            Action.AuthFailure(result.authResponse.message)
                        }
                    is AuthUseCase.Result.Error ->
                        Action.AuthFailure("Ошибка подключения")
                    else -> Action.AuthFailure("Ошибка подключения")
                }
                sendAction(action)
            }
        }
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "Авторизация успешна"
    ) : BaseViewState

    interface Action : BaseAction {
        object AuthSuccess : Action
        class AuthFailure(val message: String) : Action
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.AuthSuccess -> {
            state.copy(
                isLoading = false,
                isError = false
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