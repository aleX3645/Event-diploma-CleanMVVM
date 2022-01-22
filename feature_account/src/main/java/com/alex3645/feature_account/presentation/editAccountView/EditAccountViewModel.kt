package com.alex3645.feature_account.presentation.editAccountView

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_account.di.component.DaggerAccountViewModelComponent
import com.alex3645.feature_account.di.module.AccountViewModelModule
import com.alex3645.feature_account.domain.model.User
import com.alex3645.feature_account.usecase.EditAccountUseCase
import com.alex3645.feature_account.usecase.LoadAccountByLoginUseCase
import com.alex3645.feature_account.usecase.UploadPictureToServer
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URI
import javax.inject.Inject

class EditAccountViewModel(application: Application) : BaseAndroidViewModel<EditAccountViewModel.ViewState, EditAccountViewModel.Action>(ViewState(), application){

    init{
        DaggerAccountViewModelComponent.factory().create(AccountViewModelModule(application)).inject(this)
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
    ) : BaseViewState

    interface Action : BaseAction {
        object UserEditSuccess : Action
        object UserEditFailure : Action
    }

    @Inject
    lateinit var editAccountByLoginUseCase: EditAccountUseCase

    @Inject
    lateinit var loadAccountByLoginUseCase: LoadAccountByLoginUseCase

    @Inject
    lateinit var uploadPictureToServer: UploadPictureToServer

    fun editAccount(user: User){
        val application: Application = this.getApplication()
        viewModelScope.launch {
            val spManager = SharedPreferencesManager(application)
            editAccountByLoginUseCase(spManager.fetchLogin() ?: "", user).also { result ->
                val action = when (result) {
                    is EditAccountUseCase.Result.Success ->
                        Action.UserEditSuccess
                    is EditAccountUseCase.Result.Error ->
                        Action.UserEditFailure
                    else -> Action.UserEditFailure
                }
                sendAction(action)
            }
        }
    }

    fun editAccountWithImage(user: User, stream: InputStream){
        val application: Application = this.getApplication()
        viewModelScope.launch {
            val spManager = SharedPreferencesManager(application)
            uploadPictureToServer(spManager.fetchAuthToken()?:"",stream).also { result ->
                when (result) {
                    is UploadPictureToServer.Result.Success ->{
                        user.photoUrl = ServerConstants.LOCAL_SERVER + "/api/usr/getPictureById/" + result.response.message
                        Log.d("!!!", user.photoUrl)
                        editAccount(user)
                    }
                    is UploadPictureToServer.Result.Error ->
                        sendAction(Action.UserEditFailure)
                    else -> sendAction(Action.UserEditFailure)
                }
            }
        }
    }

    val user: MutableLiveData<User> by lazy {
        MutableLiveData()
    }

    fun loadUser(){
        val application: Application = this.getApplication()
        viewModelScope.launch {
            val spManager = SharedPreferencesManager(application)
            loadAccountByLoginUseCase(spManager.fetchLogin() ?: "").also { result ->
                if(result is LoadAccountByLoginUseCase.Result.Success){
                    user.value = result.user
                }else{
                    sendAction(Action.UserEditFailure)
                }
            }
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.UserEditSuccess -> {
            state.copy(
                isLoading = false,
                isError = false
            )
        }
        is Action.UserEditFailure -> {
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
        navController.popBackStack()
    }
}