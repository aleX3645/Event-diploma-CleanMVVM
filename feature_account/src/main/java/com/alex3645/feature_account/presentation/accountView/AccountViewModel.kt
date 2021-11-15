package com.alex3645.feature_account.presentation.accountView

import android.app.Application
import androidx.lifecycle.viewModelScope

import androidx.navigation.NavController
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_account.di.component.DaggerAccountViewModelComponent
import com.alex3645.feature_account.di.module.AccountViewModelModule
import com.alex3645.feature_account.domain.model.User
import com.alex3645.feature_account.usecase.LoadAccountByLoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject


class AccountViewModel(application: Application) : BaseAndroidViewModel<AccountViewModel.ViewState, AccountViewModel.Action>(ViewState(), application){

    init{
        DaggerAccountViewModelComponent.factory().create(AccountViewModelModule(application)).inject(this)
    }

    @Inject
    lateinit var loadAccountByLoginUseCase: LoadAccountByLoginUseCase

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val user: User? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class UserLoadingSuccess(val user: User) : Action
        object LoadingFailure : Action
    }

    fun isUserAuthed() : Boolean{
        val spManager = SharedPreferencesManager(this.getApplication())
        return spManager.fetchLogin() != null
    }

    fun loadUser(){
        val application: Application = this.getApplication()
        viewModelScope.launch {
            val spManager = SharedPreferencesManager(application)
            loadAccountByLoginUseCase(spManager.fetchLogin() ?: "").also { result ->
                val action = when (result) {
                    is LoadAccountByLoginUseCase.Result.Success ->
                        Action.UserLoadingSuccess(result.user)
                    is LoadAccountByLoginUseCase.Result.Error ->
                        Action.LoadingFailure
                    else -> Action.LoadingFailure
                }
                sendAction(action)
            }
        }
    }

    fun navigateToAccountAuth(navController: NavController){
        val action = AccountFragmentDirections.actionAccountToAuth()
        navController.navigate(action)
    }

    fun navigateToSettings(navController: NavController){
        val action = AccountFragmentDirections.actionAccountToSettings()
        navController.navigate(action)
    }

    override fun onReduceState(viewAction: Action) = when (viewAction){
        is Action.UserLoadingSuccess -> {
            state.copy(
                isLoading = false,
                isError = false,
                user = viewAction.user
            )
        }
        is Action.LoadingFailure -> {
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
}