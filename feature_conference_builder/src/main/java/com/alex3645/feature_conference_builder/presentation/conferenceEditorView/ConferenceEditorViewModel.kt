package com.alex3645.feature_conference_builder.presentation.conferenceEditorView

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.Event
import com.alex3645.feature_conference_builder.domain.model.Tariff
import com.alex3645.feature_conference_builder.usecase.SaveConferenceUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    @Inject
    lateinit var saveConferenceUseCase: SaveConferenceUseCase

    fun saveConference(){
        viewModelScope.launch {
            saveConferenceUseCase(conference).also { result ->
                val action = when (result) {
                    is SaveConferenceUseCase.Result.Success ->
                        if (result.response.success) {
                            Action.BuildSuccess
                        } else {
                            Action.BuildFailure(result.response.message)
                        }
                    is SaveConferenceUseCase.Result.Error ->
                        Action.BuildFailure("Ошибка сервера")
                    else -> Action.BuildFailure("Ошибка подключения")
                }
                sendAction(action)
            }
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.BuildSuccess -> {
            state.copy(
                isLoading = false,
                isError = false
            )
        }
        is Action.BuildFailure -> {
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

    fun navigateToEventListEditor(navController: NavController){
        val action = ConferenceEditorFragmentDirections.actionConferenceBuilderToEventEditorFragment(conference,null)
        navController.navigate(action)
    }
}