package com.alex3645.feature_search.di.module

import com.alex3645.feature_search.presentation.searchView.recycler.SearchEventAdapter
import com.alex3645.feature_search.presentation.searchView.recycler.SearchUserAdapter
import com.alex3645.feature_search.presentation.searchView.recycler.SearchConferenceAdapter
import dagger.Module
import dagger.Provides

@Module
class SearchFragmentModule {
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