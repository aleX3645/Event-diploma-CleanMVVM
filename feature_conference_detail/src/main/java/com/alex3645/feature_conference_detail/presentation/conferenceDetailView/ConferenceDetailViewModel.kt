package com.alex3645.feature_conference_detail.presentation.conferenceDetailView

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_detail.R
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailViewModelComponent
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.presentation.conferenceDetailHolderView.ConferenceDetailHolderFragmentDirections
import com.alex3645.feature_conference_detail.usecase.LoadConferenceByIdUseCase
import com.alex3645.feature_conference_detail.usecase.LoadPictureByUrlUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConferenceDetailViewModel: BaseViewModel<ConferenceDetailViewModel.ViewState, ConferenceDetailViewModel.Action>(ViewState()) {

    init{
        DaggerConferenceDetailViewModelComponent.factory().create().inject(this)
    }

    var conferenceId: Int = 0

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "Авторизация успешна",
        val conference: Conference? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class LoadSuccess(val conference: Conference) : Action
        class AuthFailure(val message: String) : Action
    }

    @Inject
    lateinit var loadConferenceByIdUseCase: LoadConferenceByIdUseCase
    @Inject
    lateinit var loadPictureByUrlUseCase: LoadPictureByUrlUseCase

    fun loadConference(imageView: ImageView){
        viewModelScope.launch {
            loadConferenceByIdUseCase(conferenceId).also { result ->
                when (result) {
                    is LoadConferenceByIdUseCase.Result.Success ->{
                        loadPicture(imageView, result.data.photoUrl)
                        sendAction(Action.LoadSuccess(result.data))
                    }
                    is LoadConferenceByIdUseCase.Result.Error ->
                        sendAction(Action.AuthFailure("Ошибка подключения"))
                    else -> sendAction(Action.AuthFailure("Ошибка подключения"))
                }
            }
        }
    }

    private fun loadPicture(imageView: ImageView, url: String){
        viewModelScope.launch {
            loadPictureByUrlUseCase(url,imageView)
        }
    }

    fun navigateToTariffs(navController: NavController){
        val action = ConferenceDetailHolderFragmentDirections.actionDetailHolderToTariffList(conferenceId)
        navController.navigate(action)
    }

    fun navigateToMap(navController: NavController, place: String){
        val action = ConferenceDetailHolderFragmentDirections.actionHolderToMap(place)
        navController.navigate(action)
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.LoadSuccess -> {
            state.copy(
                isLoading = false,
                isError = false,
                conference = viewAction.conference
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
}