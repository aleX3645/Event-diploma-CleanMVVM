package com.alex3645.feature_conference_detail.di.component

import com.alex3645.feature_conference_detail.di.module.ConferenceDetailViewModelModule
import com.alex3645.feature_conference_detail.presentation.conferenceChatView.ConferenceChatViewModel
import com.alex3645.feature_conference_detail.presentation.conferenceDetailView.ConferenceDetailViewModel
import com.alex3645.feature_conference_detail.presentation.conferenceStatsView.ConferenceStatsViewModel
import com.alex3645.feature_conference_detail.presentation.eventDetailView.EventDetailViewModel
import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.EventRecyclerFragment
import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.EventRecyclerViewModel
import com.alex3645.feature_conference_detail.presentation.tariffListView.TariffListViewModel
import dagger.Component

@Component(modules = [ConferenceDetailViewModelModule::class])
interface ConferenceDetailViewModelComponent {
    @Component.Factory
    interface Factory {
        fun create(): ConferenceDetailViewModelComponent
    }

    fun inject(conferenceDetailViewModel: ConferenceDetailViewModel)
    fun inject(eventRecyclerViewModel: EventRecyclerViewModel)
    fun inject(eventDetailViewModel: EventDetailViewModel)
    fun inject(chatViewModel: ConferenceChatViewModel)
    fun inject(tariffListViewModel: TariffListViewModel)
    fun inject(conferenceStatsViewModel: ConferenceStatsViewModel)
}