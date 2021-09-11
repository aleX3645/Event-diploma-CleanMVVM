package com.alex3645.feature_conference_list.di.module

import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.recyclerView.ConferenceAdapter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun provideAdapter(): ConferenceAdapter {
        return ConferenceAdapter()
    }
}