package com.alex3645.feature_conference_builder.presentation.conferenceEditorListView

import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Event
import com.alex3645.feature_conference_builder.presentation.conferenceEditorView.ConferenceEditorViewModel

class EventEditorListViewModel :
    BaseViewModel<EventEditorListViewModel.ViewState, EventEditorListViewModel.Action>(ViewState()){

    var conference: Conference? = null
    var event: Event? = null

    //var newEvent = Event(0, "", "", "", listOf<Event>(), "", 0)

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = ""
    ) : BaseViewState

    interface Action : BaseAction {
        object BuildSuccess : Action
        class BuildFailure(val message: String) : Action
    }

    override fun onReduceState(viewAction: Action): ViewState {
        TODO("Not yet implemented")
    }

    fun navigateToEventEditor(navController: NavController){
        if(event != null){
            val action = EventEditorListFragmentDirections.actionEventBuilderListToEventBuilderFragment(event, null)
            navController.navigate(action)
        }

        if(conference != null){
            val action = EventEditorListFragmentDirections.actionEventBuilderListToEventBuilderFragment(null, conference)
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