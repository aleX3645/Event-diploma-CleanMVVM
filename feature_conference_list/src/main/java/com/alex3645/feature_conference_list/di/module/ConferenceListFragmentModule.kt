package com.alex3645.feature_conference_list.di.module

import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.recyclerView.ConferenceAdapter
import com.alex3645.feature_conference_list.presentation.searchView.recycler.SearchConferenceAdapter
import com.alex3645.feature_conference_list.presentation.searchView.recycler.SearchEventAdapter
import com.alex3645.feature_conference_list.presentation.searchView.recycler.SearchUserAdapter
import dagger.Module
import dagger.Provides

@Module
class ConferenceListFragmentModule {

    @Provides
    fun provideConferenceAdapter(): ConferenceAdapter {
        return ConferenceAdapter()
    }

    @Provides
    fun provideSearchConferenceAdapter(): SearchConferenceAdapter {
        return SearchConferenceAdapter()
    }

    @Provides
    fun provideSearchEventAdapter(): SearchEventAdapter {
        return SearchEventAdapter()
    }

    @Provides
    fun provideSearchUserAdapter(): SearchUserAdapter {
        return SearchUserAdapter()
    }
}