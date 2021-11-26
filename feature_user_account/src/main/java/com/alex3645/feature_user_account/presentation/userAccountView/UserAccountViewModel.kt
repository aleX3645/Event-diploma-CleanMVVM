package com.alex3645.feature_user_account.presentation.userAccountView

import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_user_account.di.component.DaggerUserAccountViewModelComponent
import com.alex3645.feature_user_account.domain.data.User
import com.alex3645.feature_user_account.usecase.LoadPictureByUrlUseCase
import com.alex3645.feature_user_account.usecase.LoadUserByIdUseCase
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class UserAccountViewModel: BaseViewModel<UserAccountViewModel.ViewState, UserAccountViewModel.Action>(ViewState()) {

    init {
        DaggerUserAccountViewModelComponent.factory().create().inject(this)
    }

    @Inject
    lateinit var loadUserByIdUseCase: LoadUserByIdUseCase
    @Inject
    lateinit var loadPictureByUrlUseCase: LoadPictureByUrlUseCase

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val message: String = "",
        val user: User? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class UserLoadingSuccess(val user: User) : Action
        class LoadingFailure(val exception: Exception) : Action
    }

    fun loadUserById(id: Int, view: ImageView){
        viewModelScope.launch {
            loadUserByIdUseCase(id).also { result ->
                val action = when (result) {
                    is LoadUserByIdUseCase.Result.Success ->{
                        loadAccountImage(result.user.photoUrl,view)
                        Action.UserLoadingSuccess(result.user)
                    }
                    is LoadUserByIdUseCase.Result.Error ->
                        Action.LoadingFailure(result.e)
                    else -> Action.LoadingFailure(Exception("Произошла непредвиденная ошибка"))
                }
                sendAction(action)
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
                message = viewAction.exception.message?:"Error"
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