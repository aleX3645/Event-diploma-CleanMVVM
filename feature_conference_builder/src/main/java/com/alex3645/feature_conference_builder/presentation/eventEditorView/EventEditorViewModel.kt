package com.alex3645.feature_conference_builder.presentation.eventEditorView

import android.app.Application
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_builder.R
import com.alex3645.feature_conference_builder.di.components.DaggerBuilderViewModelComponent
import com.alex3645.feature_conference_builder.di.module.BuilderViewModelModule
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Event
import com.alex3645.feature_conference_builder.domain.model.User
import com.alex3645.feature_conference_builder.usecase.LoadAccountByLoginUseCase
import com.alex3645.feature_conference_builder.usecase.SearchUsersUseCase
import com.google.android.libraries.places.api.Places
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EventEditorViewModel(application: Application) :
    BaseAndroidViewModel<EventEditorViewModel.ViewState,EventEditorViewModel.Action>(ViewState(),application){

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val exception: Exception = Exception(),
        val user: User? = null
    ) : BaseViewState

    interface Action : BaseAction {
        class UserLoadingSuccess(val user: User) : Action
        class LoadingFailure(val exception: Exception) : Action
    }

    var conference: Conference? = null
    var event: Event? = null

    var newEvent: Event? = null

    init{
        DaggerBuilderViewModelComponent.factory().create(BuilderViewModelModule(application)).inject(this)
    }

    private val simpleDateFormatServer = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",Locale.getDefault())

    fun buildNewEvent(name: String, dateStart: Calendar, dateEnd: Calendar, description: String,speakerLogin:String,id:Int){
        newEvent = Event(
            id,
            0,
            simpleDateFormatServer.format(dateEnd.time),
            simpleDateFormatServer.format(dateStart.time),
            description,
            mutableListOf(),
            name,
            0,
            speakerLogin
        )
    }

    @Inject
    lateinit var searchUsersUseCase: SearchUsersUseCase
    @Inject
    lateinit var loadAccountByLoginUseCase: LoadAccountByLoginUseCase

    var usersPrediction: MutableLiveData<List<User>> = MutableLiveData()
    fun searchUsers(text: String){
        viewModelScope.launch {
            searchUsersUseCase(text).also { result ->
                when (result) {
                    is SearchUsersUseCase.Result.Success ->{
                        usersPrediction.postValue(result.userList)
                    }
                }
            }
        }
    }

    fun loadUserByLogin(login: String){
        viewModelScope.launch (Dispatchers.Main){
            loadAccountByLoginUseCase(login).also { result ->
                val action = when (result) {
                    is LoadAccountByLoginUseCase.Result.Success ->{
                        Action.UserLoadingSuccess(result.user)
                    }
                    is LoadAccountByLoginUseCase.Result.Error ->
                        Action.LoadingFailure(result.e)
                    else -> Action.LoadingFailure(Exception("Произошла непредвиденная ошибка"))
                }
                sendAction(action)
            }
        }
    }

    fun navigateToEventListEditor(navController: NavController){
        val action = EventEditorFragmentDirections.actionEventBuilderToEventBuilderListFragment(null,newEvent)
        navController.navigate(action)
    }

    fun navigateBack(navController: NavController){
        newEvent?.let { itNewEvent ->
            conference?.let {
                itNewEvent.conferenceId = it.id
                it.events!!.add(itNewEvent)
                navController.previousBackStackEntry?.savedStateHandle?.set("conference", conference)
                navController.popBackStack()
            }

            event?.let{
                it.events!!.add(itNewEvent)
                navController.previousBackStackEntry?.savedStateHandle?.set("event", event)
                navController.popBackStack()
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
                exception = viewAction.exception
            )
        }
        else -> state.copy(
            isLoading = false,
            isError = true
        )
    }
}