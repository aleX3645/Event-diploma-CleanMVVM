package com.alex3645.feature_conference_detail.di.component

import com.alex3645.feature_conference_detail.di.module.ConferenceDetailFragmentModule
import com.alex3645.feature_conference_detail.presentation.conferenceChatView.ConferenceChatFragment
import com.alex3645.feature_conference_detail.presentation.conferenceStatsView.ConferenceStatsFragment
import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.EventRecyclerFragment
import com.alex3645.feature_conference_detail.presentation.settingsView.SettingsConferenceFragment
import com.alex3645.feature_conference_detail.presentation.tariffListView.TariffListFragment
import dagger.Component

@Component(modules = [ConferenceDetailFragmentModule::class])
interface ConferenceDetailFragmentComponent {
    @Component.Factory
    interface Factory {
        fun create(): ConferenceDetailFragmentComponent
    }

    fun inject(eventRecyclerFragment: EventRecyclerFragment)
    fun inject(conferenceChatFragment: ConferenceChatFragment)
    fun inject(tariffListFragment: TariffListFragment)
    fun inject(conferenceStatsFragment: ConferenceStatsFragment)
    fun inject(settingsConferenceFragment: SettingsConferenceFragment)
}