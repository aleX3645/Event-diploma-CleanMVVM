package com.alex3645.feature_conference_detail.di.component

import com.alex3645.feature_conference_detail.di.module.ConferenceDetailFragmentModule
import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.EventRecyclerFragment
import dagger.Component

@Component(modules = [ConferenceDetailFragmentModule::class])
interface ConferenceDetailFragmentComponent {
    @Component.Factory
    interface Factory {
        fun create(): ConferenceDetailFragmentComponent
    }

    fun inject(eventRecyclerFragment: EventRecyclerFragment)
}