package com.alex3645.feature_account.presentation.accountView

import android.app.Application
import android.widget.ImageView
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
import com.alex3645.feature_account.usecase.LoadPictureByUrlUseCase
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


class AccountViewModel(application: Application) : BaseAndroidViewModel<AccountViewModel.ViewState, AccountViewModel.Action>(ViewState(), application){

    init{
        DaggerAccountViewModelComponent.factory().create(AccountViewModelModule(application)).inject(this)
    }

    @Inject
    lateinit var loadAccountByLoginUseCase: LoadAccountByLoginUseCase
    @Inject
    lateinit var loadPictureByUrlUseCase: LoadPictureByUrlUseCase

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val user: User? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class UserLoadingSuccess(val user: User) : Action
        class LoadingFailure(e: Exception) : Action
    }

    fun isUserAuthed() : Boolean{
        val spManager = SharedPreferencesManager(this.getApplication())
        return spManager.fetchLogin() != null
    }

    fun loadUser(imageView: ImageView){
        val application: Application = this.getApplication()
        viewModelScope.launch {
            val spManager = SharedPreferencesManager(application)
            loadAccountByLoginUseCase(spManager.fetchLogin() ?: "").also { result ->
                when (result) {
                    is LoadAccountByLoginUseCase.Result.Success ->{
                        loadAccountImage(result.user.photoUrl,imageView)
                        sendAction(Action.UserLoadingSuccess(result.user))
                    }
                    is LoadAccountByLoginUseCase.Result.Error ->
                        sendAction(Action.LoadingFailure(result.e))
                    else -> sendAction(Action.LoadingFailure(Exception("Unexpected error")))
                }
            }
        }
    }

    private fun loadAccountImage(url:String, imageView: ImageView){
        viewModelScope.launch {
            loadPictureByUrlUseCase(url,imageView).also { result ->
                when (result) {
                    is LoadPictureByUrlUseCase.Result.Error ->
                        sendAction(Action.LoadingFailure(result.e))
                    else -> sendAction(Action.LoadingFailure(Exception("Unexpected error")))
                }
            }
        }
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

    fun navigateToAccountAuth(navController: NavController){
        val action = AccountFragmentDirections.actionAccountToAuth()
        navController.navigate(action)
    }

    fun navigateToSettings(navController: NavController){
        val action = AccountFragmentDirections.actionAccountToSettings()
        navController.navigate(action)
    }

    fun navigateBack(navController: NavController){
        navController.popBackStack()
    }
}