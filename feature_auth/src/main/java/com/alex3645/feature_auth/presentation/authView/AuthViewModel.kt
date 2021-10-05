package com.alex3645.feature_auth.presentation.authView

import android.app.Application
import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_auth.di.module.AuthViewModelModule
import com.alex3645.feature_auth.usecase.AuthUseCase
import com.alex3645.feature_conference_list.di.component.DaggerAuthViewModelComponent
import com.alex3645.feature_conference_list.presentation.authView.AuthFragment
import com.alex3645.feature_conference_list.presentation.authView.AuthFragmentDirections
import kotlinx.coroutines.launch
import java.net.PasswordAuthentication
import javax.inject.Inject

class AuthViewModel(application: Application) : BaseAndroidViewModel<AuthViewModel.ViewState, AuthViewModel.Action>(ViewState(), application) {

    init{
        DaggerAuthViewModelComponent.factory().create(AuthViewModelModule(application.applicationContext)).inject(this)
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

    fun navigateToReg(navController: NavController){
        val action = AuthFragmentDirections.actionAuthToReg()
        navController.navigate(action)
    }

    fun navigateToAccount(navController: NavController){
        val action = AuthFragmentDirections.actionAuthToAccount()
        navController.navigate(action)
    }


}