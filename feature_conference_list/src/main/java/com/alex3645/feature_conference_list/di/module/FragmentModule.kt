package com.alex3645.feature_conference_list.di.module

import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.recyclerView.ConferenceAdapter
import com.alex3645.feature_conference_list.presentation.eventRecyclerView.recyclerView.EventRecyclerAdapter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun provideConferenceAdapter(): ConferenceAdapter {
        return ConferenceAdapter()
    }

    @Provides
    fun provideEventAdapter(): EventRecyclerAdapter {
        return EventRecyclerAdapter()
    }
}