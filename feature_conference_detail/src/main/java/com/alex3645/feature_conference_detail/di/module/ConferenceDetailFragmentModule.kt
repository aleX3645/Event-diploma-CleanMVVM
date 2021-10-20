package com.alex3645.feature_conference_detail.di.module

import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.recyclerView.EventRecyclerAdapter
import dagger.Module
import dagger.Provides

@Module
class ConferenceDetailFragmentModule {

    @Provides
    fun provideEventAdapter(): EventRecyclerAdapter {
        return EventRecyclerAdapter()
    }
}