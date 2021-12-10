package com.alex3645.feature_conference_builder.presentation.eventEditorListView

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Event
import com.alex3645.feature_conference_builder.domain.model.User
import com.alex3645.feature_conference_builder.usecase.SearchUsersUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventEditorListViewModel : ViewModel(){

    var conference: Conference? = null
    var event: Event? = null

    @Inject
    lateinit var searchUsersUseCase: SearchUsersUseCase

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

    fun navigateToEvent(navController: NavController, id:Int){
        if(event != null){
            val action = EventEditorListFragmentDirections.actionEventBuilderListToEventBuilderFragment(
                conference = null,
                event = event,
                editableEventId = id
            )
            navController.navigate(action)
        }

        if(conference != null){
            val action = EventEditorListFragmentDirections.actionEventBuilderListToEventBuilderFragment(
                conference = conference,
                event = null,
                editableEventId = id
            )
            navController.navigate(action)
        }
    }

    fun navigateToEventEditor(navController: NavController){
        if(event != null){
            val action = EventEditorListFragmentDirections.actionEventBuilderListToEventBuilderFragment(
                conference = null,
                event = event,
                editableEventId = -1
            )
            navController.navigate(action)
        }

        if(conference != null){
            val action = EventEditorListFragmentDirections.actionEventBuilderListToEventBuilderFragment(
                conference = conference,
                event = null,
                editableEventId = -1
            )
            navController.navigate(action)
        }
    }

    fun navigateBack(navController: NavController){
        conference?.let {
            navController.previousBackStackEntry?.savedStateHandle?.set("conference", conference)
            navController.popBackStack()
        }

        event?.let{
            navController.previousBackStackEntry?.savedStateHandle?.set("event", event)
            navController.popBackStack()
        }
    }
}