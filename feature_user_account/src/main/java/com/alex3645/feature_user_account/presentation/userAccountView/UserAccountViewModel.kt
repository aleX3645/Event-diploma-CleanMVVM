package com.alex3645.feature_user_account.presentation.userAccountView

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_user_account.di.component.DaggerUserAccountViewModelComponent
import com.alex3645.feature_user_account.domain.data.User
import com.alex3645.feature_user_account.usecase.LoadUserByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserAccountViewModel: BaseViewModel<UserAccountViewModel.ViewState, UserAccountViewModel.Action>(ViewState()) {

    init {
        DaggerUserAccountViewModelComponent.factory().create().inject(this)
    }

    @Inject
    lateinit var loadUserByIdUseCase: LoadUserByIdUseCase

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val message: String = "",
        val user: User? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class UserLoadingSuccess(val user: User) : Action
        class LoadingFailure(val message: String) : Action
    }

    fun loadUserById(id: Int){
        viewModelScope.launch {
            loadUserByIdUseCase(id).also { result ->
                val action = when (result) {
                    is LoadUserByIdUseCase.Result.Success ->
                        Action.UserLoadingSuccess(result.user)
                    is LoadUserByIdUseCase.Result.Error ->
                        Action.LoadingFailure(result.e.message?: "")
                    else -> Action.LoadingFailure("")
                }
                sendAction(action)
            }
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
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
                isError = true,
                message = viewAction.message
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
}