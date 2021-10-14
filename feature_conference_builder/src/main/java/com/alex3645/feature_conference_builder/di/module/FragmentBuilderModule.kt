package com.alex3645.feature_conference_builder.di.module

import com.alex3645.feature_conference_builder.presentation.conferenceEditorListView.recyclerView.EventAdapter
import dagger.Module
import dagger.Provides

@Module
class FragmentBuilderModule {
    @Provides
    fun provideEventAdapter(): EventAdapter {
        return EventAdapter()
    }
}