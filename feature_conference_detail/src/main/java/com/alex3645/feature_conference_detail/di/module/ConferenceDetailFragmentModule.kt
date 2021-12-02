package com.alex3645.feature_conference_detail.di.module

import com.alex3645.feature_account.presentation.settingsView.recyclerView.SettingsAdapter
import com.alex3645.feature_conference_detail.presentation.conferenceStatsView.recyclerView.TariffStatsAdapter
import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.recyclerView.EventRecyclerAdapter
import com.alex3645.feature_conference_detail.presentation.tariffListView.recyclerView.TariffAdapter
import dagger.Module
import dagger.Provides

@Module
class ConferenceDetailFragmentModule {

    @Provides
    fun provideEventAdapter(): EventRecyclerAdapter {
        return EventRecyclerAdapter()
    }
/*
    @Provides
    fun provideTariffAdapter(): TariffAdapter {
        return TariffAdapter()
    }*/

    @Provides
    fun provideTariffStatsAdapter(): TariffStatsAdapter {
        return TariffStatsAdapter()
    }

    @Provides
    fun provideSettingsAdapter(): SettingsAdapter {
        return SettingsAdapter()
    }
}