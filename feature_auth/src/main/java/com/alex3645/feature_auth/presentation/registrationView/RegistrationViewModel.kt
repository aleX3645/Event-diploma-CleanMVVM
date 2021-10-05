package com.alex3645.feature_conference_list.presentation.registrationView

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_auth.data.model.UserRegJson
import com.alex3645.feature_auth.di.module.AuthViewModelModule
import com.alex3645.feature_auth.usecase.RegistrationUseCase
import com.alex3645.feature_conference_list.di.component.DaggerAuthViewModelComponent
import com.alex3645.feature_conference_list.presentation.authView.AuthFragmentDirections

import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel(application: Application): BaseAndroidViewModel<RegistrationViewModel.ViewState, RegistrationViewModel.Action>(ViewState(), application) {

    init{
        DaggerAuthViewModelComponent.factory().create(AuthViewModelModule(application.applicationContext)).inject(this)
    }

    @Inject
    lateinit var regUseCase: RegistrationUseCase

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "Регистрация успешна успешна"
    ) : BaseViewState

    interface Action : BaseAction {
        object RegSuccess : Action
        class RegFailure(val message: String) : Action
    }

    fun tryReg(userRegJson: UserRegJson){
        viewModelScope.launch {
            regUseCase(userRegJson).also { result ->
                val action = when (result) {
                    is RegistrationUseCase.Result.Success ->
                        if (result.regResponse.success) {
                            RegistrationViewModel.Action.RegSuccess
                        } else {
                            Action.RegFailure(result.regResponse.message)
                        }
                    is RegistrationUseCase.Result.Error ->
                        Action.RegFailure("Ошибка подключения")
                    else -> Action.RegFailure("Ошибка подключения")
                }
                sendAction(action)
            }
        }
    }

    override fun onReduceState(viewAction: Action): RegistrationViewModel.ViewState = when (viewAction){
        is Action.RegSuccess -> {
            state.copy(
                isLoading = false,
                isError = false
            )
        }
        is Action.RegFailure -> {
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

    fun navigateToAccount(navController: NavController){
        val action = AuthFragmentDirections.actionAuthToAccount()
        navController.navigate(action)
    }
}