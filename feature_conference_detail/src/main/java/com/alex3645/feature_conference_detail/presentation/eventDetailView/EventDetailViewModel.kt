package com.alex3645.feature_conference_detail.presentation.eventDetailView

import android.app.Application
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailViewModelComponent
import com.alex3645.feature_conference_detail.domain.model.Event
import com.alex3645.feature_conference_detail.domain.model.User
import com.alex3645.feature_conference_detail.presentation.conferenceDetailView.ConferenceDetailViewModel
import com.alex3645.feature_conference_detail.usecase.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventDetailViewModel(application: Application): BaseAndroidViewModel<EventDetailViewModel.ViewState, EventDetailViewModel.Action>(ViewState(), application) {

    init{
        DaggerConferenceDetailViewModelComponent.factory().create().inject(this)
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = "Авторизация успешна",
        val event: Event? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class LoadSuccess(val event: Event) : Action
        class AuthFailure(val message: String) : Action
    }

    var eventId = 0

    @Inject
    lateinit var loadEventByIdUseCase: LoadEventByIdUseCase
    @Inject
    lateinit var loadAccountByIdUseCase: LoadAccountByIdUseCase
    @Inject
    lateinit var loadPictureByUrlUseCase: LoadPictureByUrlUseCase
    @Inject
    lateinit var addToPersonalScheduleUseCase: AddToPersonalScheduleUseCase
    @Inject
    lateinit var loadAccountByLoginUseCase: LoadAccountByLoginUseCase

    fun loadEvent(id: Int, orgView: ImageView){
        viewModelScope.launch {
            loadEventByIdUseCase(id).also { result ->
                val action = when (result) {
                    is LoadEventByIdUseCase.Result.Success ->{
                        loadOrganizer(result.data.speakerId,orgView)
                        Action.LoadSuccess(result.data)
                    }
                    is LoadEventByIdUseCase.Result.Error ->
                        Action.AuthFailure("Ошибка подключения")
                    else -> Action.AuthFailure("Ошибка подключения")
                }
                sendAction(action)
            }
        }
    }

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

    fun addToPersonalSchedule(){
        val spManager = SharedPreferencesManager(this.getApplication())
        viewModelScope.launch {
            loadAccountByLoginUseCase(spManager.fetchLogin()?:"").also { result ->
                when (result) {
                    is LoadAccountByLoginUseCase.Result.Success ->{
                        addToPersonalSchedule(spManager.fetchAuthToken()?:"",result.user.id)
                    }
                    is LoadAccountByLoginUseCase.Result.Error ->
                        sendAction(Action.AuthFailure("Ошибка подключения"))
                    else -> sendAction(Action.AuthFailure("Ошибка подключения"))
                }
            }
        }
    }

    val successFlag: MutableLiveData<Boolean> = MutableLiveData()
    private fun addToPersonalSchedule(token:String, userId:Int){
        viewModelScope.launch {
            addToPersonalScheduleUseCase(token,userId,eventId).also { result ->
                when (result) {
                    is AddToPersonalScheduleUseCase.Result.Success ->{
                        successFlag.postValue(true)
                    }
                    is AddToPersonalScheduleUseCase.Result.Error ->
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

    fun navigateToEvent(navController: NavController, id: Int){
        val action = EventDetailFragmentDirections.actionEventToEventList(conferenceId = -1,eventId = id)
        navController.navigate(action)
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.LoadSuccess -> {
            state.copy(
                isLoading = false,
                isError = false,
                event = viewAction.event
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

    fun navigateBack(navController: NavController){
        navController.popBackStack()
    }
}