package com.alex3645.feature_account.presentation.accountView

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_account.di.component.DaggerAccountViewModelComponent
import com.alex3645.feature_account.di.module.AccountViewModelModule
import com.alex3645.feature_account.usecase.LoadAuthAccountUseCase
import com.alex3645.feature_auth.data.database.model.AccountEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel(application: Application) : BaseAndroidViewModel<AccountViewModel.ViewState, AccountViewModel.Action>(ViewState(), application) {

    init {
        DaggerAccountViewModelComponent.factory().create(AccountViewModelModule(application.applicationContext)).inject(this)
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val accountEntity: AccountEntity = AccountEntity("","")
    ) : BaseViewState

    interface Action : BaseAction {
        class LoadSuccess(val accountEntity: AccountEntity) : Action
        object LoadFailure : Action
    }


    @Inject
    lateinit var loadAuthAccountUseCase: LoadAuthAccountUseCase
    fun loadUserData(){
        viewModelScope.launch {
            loadAuthAccountUseCase().also { result ->
                val action = when (result) {
                    is LoadAuthAccountUseCase.Result.Success ->
                        Action.LoadSuccess(result.accountEntity)
                    is LoadAuthAccountUseCase.Result.Error ->
                        Action.LoadFailure
                    else -> Action.LoadFailure
                }
                sendAction(action)
            }
        }
    }

    fun navigateToAccountAuth(navController: NavController){
        val action = AccountFragmentDirections.actionAccountToAuth()
        navController.navigate(action)
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.LoadSuccess -> {
            state.copy(
                isLoading = false,
                isError = false,
                accountEntity = viewAction.accountEntity
            )
        }
        is Action.LoadFailure -> {
            state.copy(
                isLoading = false,
                isError = true
            )
        }
        else -> {
            state.copy(
                isLoading = false,
                isError = true
            )
        }
    }
}