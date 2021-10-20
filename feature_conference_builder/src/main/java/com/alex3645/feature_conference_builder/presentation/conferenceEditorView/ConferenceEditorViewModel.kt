package com.alex3645.feature_conference_builder.presentation.conferenceEditorView

import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Event
import com.alex3645.feature_conference_builder.domain.model.Tariff

class ConferenceEditorViewModel :
    BaseViewModel<ConferenceEditorViewModel.ViewState, ConferenceEditorViewModel.Action>(ViewState()) {

    var conference: Conference = Conference(0,0,null,null,"",
        mutableListOf<Event>(),false,"",null,0,0,listOf<Tariff>())

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

    fun navigateToEventListEditor(navController: NavController){
        val action = ConferenceEditorFragmentDirections.actionConferenceBuilderToEventEditorFragment(conference,null)
        navController.navigate(action)
    }
}