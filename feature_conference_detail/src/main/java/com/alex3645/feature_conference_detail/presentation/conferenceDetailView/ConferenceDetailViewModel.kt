package com.alex3645.feature_conference_detail.presentation.conferenceDetailView

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_detail.data.network.service.ApiRetrofitConferenceDetailService
import com.alex3645.feature_conference_detail.data.repositoryImpl.ConferenceDetailRepositoryImpl
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailViewModelComponent
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.domain.model.User
import com.alex3645.feature_conference_detail.usecase.LoadAccountByIdUseCase
import com.alex3645.feature_conference_detail.usecase.LoadConferenceByIdUseCase
import com.alex3645.feature_conference_detail.usecase.LoadPictureByUrlUseCase
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ConferenceDetailViewModel: BaseViewModel<ConferenceDetailViewModel.ViewState, ConferenceDetailViewModel.Action>(ViewState()) {

    init{
        DaggerConferenceDetailViewModelComponent.factory().create().inject(this)
    }

    var conferenceId: Int = 0

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "",
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
    @Inject
    lateinit var loadAccountByIdUseCase: LoadAccountByIdUseCase

    val organizer: MutableLiveData<User> = MutableLiveData()
    private fun loadOrganizer(id:Int, imageView: ImageView){
        viewModelScope.launch {
            loadAccountByIdUseCase(id).also { result ->
                when (result) {
                    is LoadAccountByIdUseCase.Result.Success ->{
                        organizer.postValue(result.user)
                        loadPicture(imageView, result.user.photoUrl)
                    }
                    is LoadAccountByIdUseCase.Result.Error ->
                        sendAction(Action.AuthFailure("Ошибка загрузки данных организатора"))
                    else -> sendAction(Action.AuthFailure("Ошибка загрузки данных организатора"))
                }
            }
        }
    }

    fun loadConference(conferenceImageView: ImageView,orgImageView: ImageView){
        viewModelScope.launch {
            loadConferenceByIdUseCase(conferenceId).also { result ->
                when (result) {
                    is LoadConferenceByIdUseCase.Result.Success ->{
                        loadPicture(conferenceImageView, result.data.photoUrl)
                        loadOrganizer(result.data.organizerId,orgImageView)
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
        val action = ConferenceDetailFragmentDirections.actionDetailToTariffList(conferenceId)
        navController.navigate(action)
    }

    fun navigateToEvents(navController: NavController){
        val action = ConferenceDetailFragmentDirections.actionDetailToEventList(conferenceId = conferenceId,eventId = -1)
        navController.navigate(action)
    }

    fun navigateToMap(navController: NavController, place: String){
        val action = ConferenceDetailFragmentDirections.actionDetailToMap(place)
        navController.navigate(action)
    }

    fun navigateToSettings(navController: NavController){
        val action = ConferenceDetailFragmentDirections.actionDetailToSettings(conferenceId)
        navController.navigate(action)
    }
    fun navigateBack(navController: NavController){
        navController.popBackStack()
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