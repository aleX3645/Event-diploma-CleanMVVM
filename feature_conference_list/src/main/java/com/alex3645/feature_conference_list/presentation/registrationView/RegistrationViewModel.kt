package com.alex3645.feature_conference_list.presentation.registrationView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_list.data.model.UserRegJson
import com.alex3645.feature_conference_list.di.component.DaggerViewModelComponent
import com.alex3645.feature_conference_list.presentation.authView.AuthViewModel
import com.alex3645.feature_conference_list.usecase.AuthUseCase
import com.alex3645.feature_conference_list.usecase.RegistrationUseCase
import kotlinx.coroutines.launch
import java.net.PasswordAuthentication
import javax.inject.Inject

class RegistrationViewModel: BaseViewModel<RegistrationViewModel.ViewState, RegistrationViewModel.Action>(ViewState()) {

    init{
        DaggerViewModelComponent.factory().create().inject(this)
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
}