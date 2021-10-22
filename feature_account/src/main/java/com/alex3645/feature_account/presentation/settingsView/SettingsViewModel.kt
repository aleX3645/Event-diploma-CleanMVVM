package com.alex3645.feature_account.presentation.settingsView

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_account.di.component.DaggerAccountViewModelComponent
import com.alex3645.feature_account.di.module.AccountViewModelModule
import com.alex3645.feature_account.presentation.accountView.AccountFragmentDirections
import com.alex3645.feature_account.usecase.RemoveAccountUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel(application: Application): BaseAndroidViewModel<SettingsViewModel.ViewState, SettingsViewModel.Action>(
    ViewState(), application){

    init{
        DaggerAccountViewModelComponent.factory().create(AccountViewModelModule(application)).inject(this)
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = ""
    ) : BaseViewState

    interface Action : BaseAction {
        object RemoveSuccess : Action
        object RemoveFailure : Action
    }

    @Inject
    lateinit var removeAccountUseCase: RemoveAccountUseCase

    fun removeUserData(){
        viewModelScope.launch {
            removeAccountUseCase().also { result ->
                val action = when (result) {
                    is RemoveAccountUseCase.Result.Success ->
                        Action.RemoveSuccess
                    is RemoveAccountUseCase.Result.Error ->
                        Action.RemoveFailure
                    else -> Action.RemoveFailure
                }
                sendAction(action)
            }
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.RemoveSuccess -> {
            state.copy(
                isLoading = false,
                isError = false
            )
        }
        is Action.RemoveFailure -> {
            state.copy(
                isLoading = false,
                isError = true
            )
        }
        else -> state.copy(
            isLoading = false,
            isError = true
        )
    }

    fun navigateBack(navController: NavController){
        navController.popBackStack()
    }

    fun navigateToEdit(navController: NavController){
        val action = SettingsFragmentDirections.actionSettingsToEdit()
        navController.navigate(action)
    }

}