package com.alex3645.feature_conference_list.di.component


import com.alex3645.feature_conference_list.di.module.ConferenceListFragmentModule
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.ConferenceRecyclerFragment
import com.alex3645.feature_conference_list.presentation.eventRecyclerView.EventRecyclerFragment
import com.alex3645.feature_conference_list.presentation.searchView.SearchFragment
import dagger.Component

@Component(modules = [ConferenceListFragmentModule::class])
interface ConferenceListFragmentComponent {
    @Component.Factory
    interface Factory {
        fun create(): ConferenceListFragmentComponent
    }

    fun inject(conferenceRecyclerFragment: ConferenceRecyclerFragment)
    fun inject(eventRecyclerFragment: EventRecyclerFragment)
    fun inject(searchFragment: SearchFragment)
}