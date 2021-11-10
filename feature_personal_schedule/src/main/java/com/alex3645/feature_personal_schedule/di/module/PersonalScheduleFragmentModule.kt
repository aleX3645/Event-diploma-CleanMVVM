package com.alex3645.feature_personal_schedule.di.module

import com.alex3645.feature_personal_schedule.presentation.personalScheduleView.recyclerView.EventRecyclerAdapter
import dagger.Module
import dagger.Provides

@Module
class PersonalScheduleFragmentModule {
    @Provides
    fun provideEventAdapter(): EventRecyclerAdapter {
        return EventRecyclerAdapter()
    }
}