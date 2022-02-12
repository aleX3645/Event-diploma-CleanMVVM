package com.alex3645.feature_conference_detail.presentation.conferenceStatsView

import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.domain.model.Event
import com.alex3645.feature_conference_detail.domain.model.Tariff
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

fun onReduceState(viewAction: ConferenceStatsViewModel.Action, state: ConferenceStatsViewModel.ViewState): ConferenceStatsViewModel.ViewState = when (viewAction) {
    is ConferenceStatsViewModel.Action.LoadSuccess -> {
        state.copy(
            isLoading = false,
            isError = false,
            conference = viewAction.conference
        )
    }
    is ConferenceStatsViewModel.Action.Failure -> {
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

class ConferenceStatsViewModelTest{
    //ConferenceStatsViewModel.ViewState(true,false,"errorMessage",)
    val startState = ConferenceStatsViewModel.ViewState()

    @Test
    fun `если произошла успешная загрузка то отображем список конференций`() {
        val testConference = Conference(0,"","","",
            mutableListOf<Event>(),0,false,"","",0,0,mutableListOf(),"")

        val action = ConferenceStatsViewModel.Action.LoadSuccess(testConference)

        val expectedState = startState.copy(isLoading = false, isError = false, conference = testConference)
        val actualState = onReduceState(action,startState)

        Assert.assertEquals(expectedState,actualState)
    }
}