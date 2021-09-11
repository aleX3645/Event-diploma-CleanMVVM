package com.alex3645.feature_conference_list.presentation.conferenceRecyclerView

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.usecase.LoadNextConferencesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConferenceRecyclerViewModel @Inject constructor(val loadNextConferencesUseCase: LoadNextConferencesUseCase):
    BaseViewModel<ConferenceRecyclerViewModel.ViewState, ConferenceRecyclerViewModel.Action>(ViewState()){
    val conferences: MutableLiveData<List<Conference>> = MutableLiveData()

    override fun onLoadData() {
        loadNextConferences()
    }

    override fun onReduceState(viewAction: Action) = when (viewAction){
        is Action.ConferenceListLoadingSuccess -> state.copy(
            isLoading = false,
            isError = false,
            conferences = viewAction.conferences
        )
        is Action.ConferenceListLoadingFailure -> state.copy(
            isLoading = false,
            isError = true,
            conferences = listOf()
        )
        else -> state.copy(
            isLoading = false,
            isError = true,
            conferences = listOf()
        )
    }

    fun loadNextConferences(){
        viewModelScope.launch {
            loadNextConferencesUseCase().also { result ->
                val action = when (result) {
                    is LoadNextConferencesUseCase.Result.Success ->
                        if (result.data.isEmpty()) {
                            Action.ConferenceListLoadingFailure
                        } else {
                            Action.ConferenceListLoadingSuccess(result.data)
                        }
                    is LoadNextConferencesUseCase.Result.Error ->
                        Action.ConferenceListLoadingFailure
                    else -> Action.ConferenceListLoadingFailure
                }
                sendAction(action)
            }
        }
    }


    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val conferences: List<Conference> = listOf()
    ) : BaseViewState

    interface Action : BaseAction {
        class ConferenceListLoadingSuccess(val conferences: List<Conference>) : Action
        object ConferenceListLoadingFailure : Action
    }
}